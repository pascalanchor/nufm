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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import avh.nufm.api.common.PathCte;
import avh.nufm.api.email.EmailBuilder;
import avh.nufm.api.email.EmailService;
import avh.nufm.api.impl.UserControllerImpl;
import avh.nufm.api.impl.services.FileStorageService;
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
	@Autowired ContractorControllerImpl ccImpl;
	@Autowired EmailService emailSender;
	@Autowired private EmailBuilder emailBuilder;
	@Autowired private FileStorageService fss;
	//ADD CONTRACTOR
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping(PathCte.AddContractorServletPath)
    public ResponseEntity<String> createContractor(@RequestParam("profileImage") MultipartFile profileImage,@RequestParam("data") String data) {
    	try {
    		//image storage path 
    		String path = "D:\\AVH projects\\Workspaces\\NufmWorkspace\\nufm\\code\\avh.nufm\\src\\main\\resources\\storage\\profile\\contractor";
    		APIContractorIn contractorIn = new ObjectMapper().readValue(data, APIContractorIn.class);
    		// create the user without roles
    		NufmUser contractorModel = ContractorTransformer.ModelFromContractor(contractorIn);
    		String imagePath = fss.storeFile(profileImage, path);		
    		contractorModel.setProfileImage(imagePath);
    		String pwd = ccImpl.createContractor(contractorModel);
    		// add role 'ROLE_PROPERTY_CONTRACTOR' to the created user
    		ucImpl.addRoleToUser(contractorIn.getEmail(), SecurityCte.RoleContractor);
    		// add specializations to contractor
    		ucImpl.addSpecializations(contractorIn.getEmail(), contractorIn.getSpecializations());
    		ConfirmationToken ct = new ConfirmationToken();
    		ct.setIid(UUID.randomUUID().toString());
    		ct.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
    		ct.setExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
    		ct.setNufmUser(ccImpl.getContractorById(contractorIn.getEmail()));
    		String tok = UUID.randomUUID().toString();
    		ct.setToken(tok);
    		rep.getConfirmationTokenRepo().save(ct);
    		String link = "http://localhost:6338"+SecurityCte.PublicServletPath+"/register/confirm/" + tok;
    		String mail = emailBuilder.confirmContractor(contractorIn.getFullName(),contractorIn.getEmail(),pwd,link);
    		emailSender.send(contractorIn.getEmail(), mail);    		
    		return ResponseEntity.ok().body("A confirmation email is sent to  "+contractorIn.getEmail()+"||token = "+tok);
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    //DELETE CONTRACTOR
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@DeleteMapping(PathCte.DeleteContractorServletPath+"/{id}")
    public ResponseEntity<String> deleteContractor(@PathVariable("id") String id){
    	try {
    		ccImpl.deleteContractor(id);
    		return ResponseEntity.ok().body("contractor has been deleted successfully");
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    //GET CONTRACTORS
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
    @GetMapping(PathCte.GetContractorsServletPath)
    public ResponseEntity<List<APIContractorOut>> getContractors(){
    	try {
    		List<NufmUser> contractors = ccImpl.getContractors();
    		List<APIContractorOut> res = new ArrayList<>();
    		contractors.stream().forEach(e-> res.add(ContractorTransformer.ContractorFromModel(e)));
    		return ResponseEntity.ok().body(res);
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    //GET CONTRACTOR BY ID
    @PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
    @GetMapping(PathCte.ContractorServletPath+"/{id}")
    public ResponseEntity<APIContractorOut> getContractorById(@PathVariable("id") String id){
    	try {
    		NufmUser res = ccImpl.getContractorById(id);
    		return ResponseEntity.ok().body(ContractorTransformer.ContractorFromModel(res));
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
    }
    //UPDATE WORKER
    @PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
    @PutMapping(PathCte.EditContractorServletPath)
    public ResponseEntity<String> updateContractor(@RequestParam("profileImage") MultipartFile profileImage,@RequestParam("data") String data){
    	try {
    	//image storage path 
		String path = "D:\\AVH projects\\Workspaces\\NufmWorkspace\\nufm\\code\\avh.nufm\\src\\main\\resources\\storage\\profile\\contractor";
		APIContractorIn contractorIn = new ObjectMapper().readValue(data, APIContractorIn.class); 
		// create the user without roles
		NufmUser contractorModel = ContractorTransformer.ModelFromContractor(contractorIn);
		String imagePath = fss.storeFile(profileImage, path);		
		contractorModel.setProfileImage(imagePath);
		ccImpl.updateContractor(contractorModel);
		// add specializations to contractor
		ucImpl.addSpecializations(contractorIn.getEmail(), contractorIn.getSpecializations());		
		return ResponseEntity.ok().body("contractor is updated");
	} catch(Exception e) {
		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
	}
    }
    }
    
    
    

