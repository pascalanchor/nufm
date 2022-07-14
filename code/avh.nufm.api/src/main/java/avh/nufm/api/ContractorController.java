package avh.nufm.api;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.common.PathCte;
import avh.nufm.api.email.EmailBuilder;
import avh.nufm.api.email.EmailService;
import avh.nufm.api.impl.UserControllerImpl;
import avh.nufm.api.impl.ContractorControllerImpl;
import avh.nufm.api.model.in.APIContractorIn;
import avh.nufm.api.model.out.APIContractorOut;
import avh.nufm.api.model.transformer.ContractorTransformer;
import avh.nufm.business.model.ConfirmationToken;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.repository.NufmRepos;
import avh.nufm.security.common.SecurityCte;

@RestController
public class ContractorController {
	@Autowired NufmRepos rep;
	@Autowired UserControllerImpl ucImpl;
	@Autowired ContractorControllerImpl wcImpl;
	@Autowired EmailService emailSender;
	@Autowired private EmailBuilder emailBuilder;
	
	//ADD WORKER
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping(PathCte.AddContractorServletPath)
    public ResponseEntity<String> createContractor(@RequestBody APIContractorIn contractorIn) {
    	try {
    		// create the user without roles
    		NufmUser contractorModel = ContractorTransformer.ModelFromContractor(contractorIn);
    		String pwd = wcImpl.createContractor(contractorModel);
    		// add role 'ROLE_PROPERTY_WORKER' to the created user
    		ucImpl.addRoleToUser(contractorIn.getEmail(), SecurityCte.RoleContractor);
    		// add specializations to contractor
    		ucImpl.addSpecializations(contractorIn.getEmail(), contractorIn.getSpecializations());
    		ConfirmationToken ct = new ConfirmationToken();
    		ct.setIid(UUID.randomUUID().toString());
    		ct.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
    		ct.setExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
    		ct.setUserId(contractorIn.getEmail());
    		String tok = UUID.randomUUID().toString();
    		ct.setToken(tok);
    		rep.getConfirmationTokenRepo().save(ct);
    		String link = "http://localhost:6338"+SecurityCte.PublicServletPath+"/register/confirm/" + tok;
    		String mail = emailBuilder.confirmContractor(contractorIn.getFullName(),contractorIn.getEmail(),pwd,link);
    		emailSender.send(contractorIn.getEmail(), mail);    		
    		return ResponseEntity.ok().body("A confirmation email is sent to  "+contractorIn.getFullName()+"||token = "+tok);
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    //DELETE WORKER
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@DeleteMapping(PathCte.DeleteContractorServletPath+"/{contractorId}")
    public ResponseEntity<String> deleteContractor(@PathVariable("contractorId") String contractorId){
    	try {
    		wcImpl.deleteContractor(contractorId);
    		return ResponseEntity.ok().body("contractor has been deleted successfully");
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    //GET WORKERS
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
    @GetMapping(PathCte.GetContractorsServletPath)
    public ResponseEntity<List<APIContractorOut>> getContractors(){
    	try {
    		List<NufmUser> contractors = wcImpl.getContractors();
    		List<APIContractorOut> res = new ArrayList<>();
    		contractors.stream().forEach(e-> res.add(ContractorTransformer.ContractorFromModel(e)));
    		return ResponseEntity.ok().body(res);
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    //GET WORKER BY ID
    @PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
    @GetMapping(PathCte.ContractorServletPath+"/{contractorId}")
    public ResponseEntity<APIContractorOut> getContractorById(@PathVariable("contractorId") String contractorId){
    	try {
    		NufmUser res = wcImpl.getContractorById(contractorId);
    		return ResponseEntity.ok().body(ContractorTransformer.ContractorFromModel(res));
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    //UPDATE WORKER
    @PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
    @PutMapping(PathCte.EditContractorServletPath)
    public ResponseEntity<APIContractorOut> updateContractor(@RequestBody APIContractorIn contractorUpdate){
    	try {
    		NufmUser contractor = ContractorTransformer.ModelFromContractor(contractorUpdate);
    	    NufmUser res = wcImpl.updateContractor(contractor, contractorUpdate.getSpecializations());
    	    return ResponseEntity.ok().body(ContractorTransformer.ContractorFromModel(res));
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    
    
    
}
