package avh.nufm.api;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.ConfirmationTokenControllerImpl;
import avh.nufm.api.impl.UserControllerImpl;
import avh.nufm.business.model.ConfirmationToken;
import avh.nufm.business.model.NufmUser;
import avh.nufm.security.common.SecurityCte;

@RestController
public class ConfirmationTokenController {
	@Autowired
	private ConfirmationTokenControllerImpl svc;
	@Autowired
	private UserControllerImpl userSvc;
	
	@GetMapping(SecurityCte.PublicServletPath+"/register/confirm/{token}")
	public ResponseEntity<String> confirmToken(@PathVariable("token") String token) {
		try {
			ConfirmationToken ct = svc.getToken(token);
			if(ct.equals(null)) {
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("Token not found"));
			}
			if(ct.getConfirmedAt() != null) {
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("Email already confirmed"));
			}
			Timestamp expiredAt = ct.getExpiredAt();
			if(expiredAt.before(Timestamp.valueOf(LocalDateTime.now()))) {
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("Token expired"));
			}
			svc.updateToken(ct,ct.getIid(),token,ct.getCreatedAt());
			NufmUser user = userSvc.getUser(ct.getNufmUser().getEid());
			userSvc.updateUserEnabled(user);
			return ResponseEntity.ok().body("Confirmed");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
	}
	
	
}
