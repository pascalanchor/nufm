package avh.nufm.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.SpecializationControllerImpl;
import avh.nufm.security.common.SecurityCte;

@RestController
public class SpecializationController {
	@Autowired SpecializationControllerImpl scImpl;

	@GetMapping(SecurityCte.PublicServletPath+"/specializations/options/worker")
	public ResponseEntity<List<String>> getWorkerSpecOptions(){
		try {
			return ResponseEntity.ok().body(scImpl.getWorkerSpecOptions());
		} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
	}
	
	@GetMapping(SecurityCte.PublicServletPath+"/specializations/options/contractor")
	public ResponseEntity<List<String>> getContractorSpecOptions(){
		try {
			return ResponseEntity.ok().body(scImpl.getContractorSpecOptions());
		} catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    	}
	}
}
