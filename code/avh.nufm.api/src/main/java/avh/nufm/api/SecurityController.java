package avh.nufm.api;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.business.model.ConfirmationToken;
import avh.nufm.business.model.UserRole;
import avh.nufm.business.model.NufmRole;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.repository.NufmRepos;
import avh.nufm.security.jwt.JWTProvider;
import avh.nufm.security.common.SecurityCte;
import avh.nufm.api.email.EmailBuilder;
import avh.nufm.api.email.EmailService;
import avh.nufm.api.model.in.APIUserIn;
import avh.nufm.api.model.in.ForgetPasswordIn;
import avh.nufm.api.model.in.ResetPasswordIn;
import avh.nufm.api.model.out.APIUserOut;
import avh.nufm.api.model.transformer.UserTransformer;

@RestController
public class SecurityController {

	@Autowired ConfirmationTokenController confirmationTokenController;
	@Autowired AuthenticationManager authenticationManager;
    @Autowired JWTProvider tokenProvider;
	@Autowired NufmRepos rep;
	@Autowired PasswordEncoder pHasher;
	@Autowired EmailService emailSender;
	@Autowired private EmailBuilder emailBuilder;
	
    @PostMapping(SecurityCte.LoginServletPath)
    public ResponseEntity<APIUserOut> login(@RequestParam String username, @RequestParam String password) {
    	try {
    		UsernamePasswordAuthenticationToken atk = new UsernamePasswordAuthenticationToken(username, password);
    		Authentication authentication = authenticationManager.authenticate(atk);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            NufmUser usr = rep.getNfuserrepo().findById(username).get();
            if(usr.getEnabled().equals(false)) {
            	throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("the user %s is not confirmed", username));
            }
            APIUserOut lr = UserTransformer.UserFromModel(usr);
            List<UserRole> mbs = rep.getUserrolerepo().findByNufmUser(usr);
            mbs.stream().forEach(m -> lr.addRole(m.getNufmRole().getName()));
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).body(lr);
    	} catch (InternalAuthenticationServiceException e) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("the user %s is not defined", username));
    	} catch (BadCredentialsException bce) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("wrong password for the user %s", username));
    	}
    }
    
    @Transactional
    @PostMapping(SecurityCte.RegisterServletPath)
    public String registerUser(@RequestBody APIUserIn usr) {
    	try {
    		// any registered user with the same name ?
    		Optional<NufmUser> ou = rep.getNfuserrepo().findById(usr.getEmail());
    		if (ou.isPresent())
    			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("the user %s is already registered", usr.getEmail()));
    		// create the user without roles
    		NufmUser res = UserTransformer.ModelFromUser(usr);
    		res.setEid(usr.getEmail());
    		res.setEnabled(false);
    		res.setCreatedAt(new Timestamp(System.currentTimeMillis()));    		
    		res.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    		res.setPassword(pHasher.encode(usr.getPassword()));
    		rep.getNfuserrepo().save(res);
    		// now add the membership according to incoming roles
    		for(String r : usr.getRoles())
    		{Optional<NufmRole> oRole = rep.getNfrolerepo().findById(r);
    		if(oRole.isEmpty())
    			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("the role %s cannot be found", r));
    		NufmRole role = oRole.get();
    		UserRole mb = new UserRole();
    		mb.setEid(UUID.randomUUID().toString());
    		mb.setNufmRole(role);
    		mb.setNufmUser(res);  
    		mb.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
    		rep.getUserrolerepo().save(mb);}

    		ConfirmationToken ct = new ConfirmationToken();
    		ct.setIid(UUID.randomUUID().toString());
    		ct.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
    		ct.setExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
    		ct.setUserId(usr.getEmail());
    		String tok = UUID.randomUUID().toString();
    		ct.setToken(tok);
    		rep.getConfirmationTokenRepo().save(ct);

    		String link = "http://localhost:6338"+SecurityCte.PublicServletPath+"/register/confirm/" + tok;
    		String mail = emailBuilder.activateEmail(usr.getFullName(), link);
    		emailSender.send(usr.getEmail(), mail);
    		
    		return tok;
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    
    @PutMapping(SecurityCte.ResetPasswordServletPath)
    public boolean resetPassword(@RequestBody ResetPasswordIn rpi) {
    	try {
            NufmUser usr = rep.getNfuserrepo().findByEid(rpi.getEmail());
            if(usr.equals(null)) {
            	throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("the user %s does not exist", rpi.getEmail()));
            }
            boolean match = pHasher.matches(rpi.getOldPassword(), usr.getPassword());
            if(match == false) {
            	throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("the old password is wrong"));
            }
            usr.setPassword(pHasher.encode(rpi.getNewPassword()));
            usr.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            rep.getNfuserrepo().save(usr);
            return true;
    	} catch (InternalAuthenticationServiceException e) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("the user %s is not defined", rpi.getEmail()));
    	}
    }
    
    @PostMapping(SecurityCte.ForgetPasswordServletPath)
    public boolean forgetPassword(@RequestBody APIUserIn usr) {
    	try {
            NufmUser nu = rep.getNfuserrepo().findByEid(usr.getEmail());
            if(nu.equals(null)) {
            	throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("the user %s does not exist", usr.getEmail()));
            }
            nu.setEnabled(false);
            rep.getNfuserrepo().save(nu);
            ConfirmationToken ct = new ConfirmationToken();
    		ct.setIid(UUID.randomUUID().toString());
    		ct.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
    		ct.setExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
    		ct.setUserId(nu.getEid());
    		String tok = UUID.randomUUID().toString();
    		ct.setToken(tok);
    		rep.getConfirmationTokenRepo().save(ct);
    		
    		String link = "http://localhost:6338/api/public/forgetPassword/redirect/" + tok;
    		String mail = emailBuilder.forgetPasswordEmail(nu.getFullName(), link);
    		emailSender.send(usr.getEmail(), mail);
    		
            return true;
    	} catch (InternalAuthenticationServiceException e) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("the user %s is not defined", usr.getEmail()));
    	}
    }
    
    @PutMapping("api/public/forgetPassword/redirect/{token}")
    public String forgetPasswordRedirection(@RequestBody ForgetPasswordIn fpi, @PathVariable("token") String token) {
    	try {
    		ConfirmationToken ct = rep.getConfirmationTokenRepo().findByToken(token);
			if(ct.equals(null)) {
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("Token not found"));
			}
//			if(ct.getConfirmedAt() != null) {
//				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("Token confirmed"));
//			}
			Timestamp expiredAt = ct.getExpiredAt();
			if(expiredAt.before(Timestamp.valueOf(LocalDateTime.now()))) {
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("Token expired"));
			}
			ct.setConfirmedAt(Timestamp.valueOf(LocalDateTime.now()));
			rep.getConfirmationTokenRepo().save(ct);
			NufmUser user = rep.getNfuserrepo().findByEid(ct.getUserId());
			if(user.equals(null)) {
            	throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("the user %s does not exist", ct.getUserId()));
            }
			boolean match = pHasher.matches(fpi.getNewPassword(), user.getPassword());
            if(match == true) {
            	throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("this is a old password"));
            }
			user.setPassword(pHasher.encode(fpi.getNewPassword()));
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            user.setEnabled(true);
            rep.getNfuserrepo().save(user);
            return "Reset Password completed";
    	} catch (InternalAuthenticationServiceException e) {
    		throw new ResponseStatusException(HttpStatus.FORBIDDEN, String.format("the user %s is not defined", token));
    	}
    }
    
}
