package avh.nufm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.FacilityControllerImpl;
import avh.nufm.api.model.Transformer;
import avh.nufm.api.model.in.FacilityIn;
import avh.nufm.api.model.out.FacilityOut;
import avh.nufm.business.model.Facility;

@RestController
public class FacilityController {
@Autowired private FacilityControllerImpl fci;



@PostMapping("avh/nufm/facility/define")//only administrator and owner can define new facility
public ResponseEntity<FacilityOut> createFacility(@RequestBody FacilityIn fc) {
	
	try {
		Facility fct=Transformer.FacilityToModel(fc);
		FacilityOut res=Transformer.FacilityFromModel(fci.createFacility(fct));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
	

}

@PutMapping("avh/nufm/facility/update")//only administrator and owner can define new facility
public ResponseEntity<FacilityOut> updateFaclity(
		@RequestParam String facility_id,
		@RequestBody FacilityIn fc) {
	
	try {
		Facility fct=Transformer.FacilityToModel(fc);
		FacilityOut fco=Transformer.FacilityFromModel(fci.updateFacility(facility_id, fct));
		return ResponseEntity.ok().body(fco);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
	
}


@GetMapping("avh/nufm/facility/getById")
public ResponseEntity<FacilityOut> getFacilityById(@RequestParam("Facility_id") String fid) {
	try {
		FacilityOut res=Transformer.FacilityFromModel(fci.getFacilityById(fid));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
}

}
