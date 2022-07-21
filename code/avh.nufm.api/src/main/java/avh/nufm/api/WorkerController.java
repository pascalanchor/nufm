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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import avh.nufm.api.common.PathCte;
import avh.nufm.api.email.EmailBuilder;
import avh.nufm.api.email.EmailService;
import avh.nufm.api.impl.UserControllerImpl;
import avh.nufm.api.impl.WorkerControllerImpl;
import avh.nufm.api.impl.services.FileStorageService;
import avh.nufm.api.model.in.APIWorkerIn;
import avh.nufm.api.model.out.APIWorkerOut;
import avh.nufm.api.model.transformer.WorkerTransformer;
import avh.nufm.business.model.ConfirmationToken;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.repository.NufmRepos;
import avh.nufm.security.common.SecurityCte;

@RestController
public class WorkerController {
	@Autowired NufmRepos rep;
	@Autowired UserControllerImpl ucImpl;
	@Autowired WorkerControllerImpl wcImpl;
	@Autowired EmailService emailSender;
	@Autowired private EmailBuilder emailBuilder;
	@Autowired private FileStorageService fss;
	
	//ADD WORKER
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping(PathCte.AddWorkerServletPath)
    public ResponseEntity<String> createWorker(@RequestParam("profileImage") MultipartFile profileImage,@RequestParam("data") String data) {
    	try {
    		//image storage path 
    		String path = "D:\\AVH projects\\Workspaces\\NufmWorkspace\\nufm\\code\\avh.nufm\\src\\main\\resources\\storage\\profile\\worker";
    		APIWorkerIn workerIn = new ObjectMapper().readValue(data, APIWorkerIn.class); 
    		// create the user without roles
    		NufmUser workerModel = WorkerTransformer.ModelFromWorker(workerIn);
    		String imagePath = fss.storeFile(profileImage, path);		
    		workerModel.setProfileImage(imagePath);
    		String pwd = wcImpl.createWorker(workerModel);
    		// add role 'ROLE_PROPERTY_WORKER' to the created user
    		ucImpl.addRoleToUser(workerIn.getEmail(), SecurityCte.RoleWorker);
    		// add specializations to worker
    		ucImpl.addSpecializations(workerIn.getEmail(), workerIn.getSpecializations());
    		ConfirmationToken ct = new ConfirmationToken();
    		ct.setIid(UUID.randomUUID().toString());
    		ct.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
    		ct.setExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
    		ct.setNufmUser(wcImpl.getWorkerById(workerIn.getEmail()));
    		String tok = UUID.randomUUID().toString();
    		ct.setToken(tok);
    		rep.getConfirmationTokenRepo().save(ct);
    		String link = "http://localhost:6338"+SecurityCte.PublicServletPath+"/register/confirm/" + tok;
    		String mail = emailBuilder.confirmWorker(workerIn.getFullName(),workerIn.getEmail(),pwd,link);
    		emailSender.send(workerIn.getEmail(), mail);    		
    		return ResponseEntity.ok().body("A confirmation email is sent to  "+workerIn.getEmail()+"||token = "+tok);
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
    	}
    }
    //DELETE WORKER
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@DeleteMapping(PathCte.DeleteWorkerServletPath+"/{id}")
    public ResponseEntity<String> deleteWorker(@PathVariable("id") String id){
    	try {
    		wcImpl.deleteWorker(id);
    		return ResponseEntity.ok().body("worker has been deleted successfully");
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
    	}
    }
    //GET WORKERS
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
    @GetMapping(PathCte.GetWorkersServletPath)
    public ResponseEntity<List<APIWorkerOut>> getWorkers(){
    	try {
    		List<NufmUser> workers = wcImpl.getWorkers();
    		List<APIWorkerOut> res = new ArrayList<>();
    		workers.stream().forEach(e-> res.add(WorkerTransformer.WorkerFromModel(e)));
    		return ResponseEntity.ok().body(res);
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
    	}
    }
    //GET WORKER BY ID
    @PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
    @GetMapping(PathCte.GetWorkerByIdServletPath+"/{id}")
    public ResponseEntity<APIWorkerOut> getWorkerById(@PathVariable("id") String id){
    	try {
    		NufmUser res = wcImpl.getWorkerById(id);
    		return ResponseEntity.ok().body(WorkerTransformer.WorkerFromModel(res));
    	} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
    	}
    }
//    //UPDATE WORKER
//    @PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
//    @PutMapping(PathCte.EditWorkerServletPath)
//    public ResponseEntity<APIWorkerOut> updateWorker(@RequestBody APIWorkerIn workerUpdate){
//    	try {
//    		NufmUser worker = WorkerTransformer.ModelFromWorker(workerUpdate);
//    	    NufmUser res = wcImpl.updateWorker(worker, workerUpdate.getSpecializations());
//    	    return ResponseEntity.ok().body(WorkerTransformer.WorkerFromModel(res));
//    	} catch (Exception e) {
//    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
//    	}
//    }
    
    
    
}
