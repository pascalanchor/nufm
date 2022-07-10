//package avh.nufm.api;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//
//import avh.nufm.api.common.PathCte;
//import avh.nufm.api.email.EmailBuilder;
//import avh.nufm.api.email.EmailService;
//import avh.nufm.api.impl.UserControllerImpl;
//import avh.nufm.api.impl.OccupantControllerImpl;
//import avh.nufm.api.model.in.APIOccupantIn;
//import avh.nufm.api.model.out.APIOccupantOut;
//import avh.nufm.api.model.transformer.OccupantTransformer;
//import avh.nufm.business.model.ConfirmationToken;
//import avh.nufm.business.model.NufmUser;
//import avh.nufm.business.model.repository.NufmRepos;
//import avh.nufm.security.common.SecurityCte;
//
//@RestController
//public class OccupantController {
//	@Autowired NufmRepos rep;
//	@Autowired UserControllerImpl ucImpl;
//	@Autowired OccupantControllerImpl wcImpl;
//	@Autowired EmailService emailSender;
//	@Autowired private EmailBuilder emailBuilder;
//	
//	//ADD OCCUPANT
//	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
//	@PostMapping(PathCte.AddOccupantServletPath)
//    public ResponseEntity<String> createOccupant(@RequestBody APIOccupantIn occupantIn) {
//    	try {
//    		// create the user without roles
//    		NufmUser occupantModel = OccupantTransformer.ModelFromOccupant(occupantIn);
//    		String pwd = wcImpl.createOccupant(occupantModel);
//    		// add role 'ROLE_PROPERTY_OCCUPANT' to the created user
//    		ucImpl.addRoleToUser(occupantIn.getEmail(), SecurityCte.RoleOccupant);
//    		// add specializations to occupant
//    		ucImpl.addSpecializations(occupantIn.getEmail(), occupantIn.getSpecializations());
//    		ConfirmationToken ct = new ConfirmationToken();
//    		ct.setIid(UUID.randomUUID().toString());
//    		ct.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
//    		ct.setExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
//    		ct.setUserId(occupantIn.getEmail());
//    		String tok = UUID.randomUUID().toString();
//    		ct.setToken(tok);
//    		rep.getConfirmationTokenRepo().save(ct);
//    		String link = "http://localhost:6338"+SecurityCte.PublicServletPath+"/register/confirm/" + tok;
//    		String mail = emailBuilder.confirmOccupant(occupantIn.getFullName(),occupantIn.getEmail(),pwd,link);
//    		emailSender.send(occupantIn.getEmail(), mail);    		
//    		return ResponseEntity.ok().body("A confirmation email is sent to  "+occupantIn.getFullName()+"||token = "+tok);
//    	} catch (Exception e) {
//    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
//    	}
//    }
//    //DELETE OCCUPANT
//	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
//	@DeleteMapping(PathCte.DeleteOccupantServletPath+"/{occupantId}")
//    public ResponseEntity<String> deleteOccupant(@PathVariable("occupantId") String occupantId){
//    	try {
//    		wcImpl.deleteOccupant(occupantId);
//    		return ResponseEntity.ok().body("occupant has been deleted successfully");
//    	} catch (Exception e) {
//    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
//    	}
//    }
//    //GET OCCUPANTS
//	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
//    @GetMapping(PathCte.GetOccupantsServletPath)
//    public ResponseEntity<List<APIOccupantOut>> getOccupants(){
//    	try {
//    		List<NufmUser> occupants = wcImpl.getOccupants();
//    		List<APIOccupantOut> res = new ArrayList<>();
//    		occupants.stream().forEach(e-> res.add(OccupantTransformer.OccupantFromModel(e)));
//    		return ResponseEntity.ok().body(res);
//    	} catch (Exception e) {
//    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
//    	}
//    }
//    //GET OCCUPANT BY ID
//    @PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
//    @GetMapping(PathCte.OccupantServletPath+"")
//    public ResponseEntity<APIOccupantOut> getOccupantById(@RequestParam String occupantId){
//    	try {
//    		NufmUser res = wcImpl.getOccupantById(occupantId);
//    		return ResponseEntity.ok().body(OccupantTransformer.OccupantFromModel(res));
//    	} catch (Exception e) {
//    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
//    	}
//    }
//    //UPDATE OCCUPANT
//    @PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
//    @PutMapping(PathCte.EditOccupantServletPath)
//    public ResponseEntity<APIOccupantOut> updateOccupant(@RequestBody APIOccupantIn occupantUpdate){
//    	try {
//    		NufmUser occupant = OccupantTransformer.ModelFromOccupant(occupantUpdate);
//    	    NufmUser res = wcImpl.updateOccupant(occupant, occupantUpdate.getSpecializations());
//    	    return ResponseEntity.ok().body(OccupantTransformer.OccupantFromModel(res));
//    	} catch (Exception e) {
//    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
//    	}
//    }
//    
//    
//    
//}
