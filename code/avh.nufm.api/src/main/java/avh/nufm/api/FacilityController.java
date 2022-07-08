package avh.nufm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.FacilityControllerImpl;
import avh.nufm.api.model.Transformer;
import avh.nufm.api.model.in.APIFacilityIn;
import avh.nufm.api.model.out.APIFacilityOut;
import avh.nufm.business.model.Facility;

@RestController
public class FacilityController {
@Autowired private FacilityControllerImpl fci;

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PostMapping("avh/nufm/v1/private/facility/add")//only administrator and owner can define new facility
public ResponseEntity<APIFacilityOut> createFacility(@RequestBody APIFacilityIn fc) {
	
	try {
		Facility fct=Transformer.FacilityToModel(fc);
		APIFacilityOut res=Transformer.FacilityFromModel(fci.createFacility(fct));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
	

}

@PutMapping("avh/nufm/v1/private/facility/update")//only administrator and owner can define new facility
public ResponseEntity<APIFacilityOut> updateFaclity(
		@RequestParam String facility_id,
		@RequestBody APIFacilityIn fc) {
	
	try {
		Facility fct=Transformer.FacilityToModel(fc);
		APIFacilityOut fco=Transformer.FacilityFromModel(fci.updateFacility(facility_id, fct));
		return ResponseEntity.ok().body(fco);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
	
}


@GetMapping("avh/nufm/v1/private/facilies/{facilityId}")
public ResponseEntity<APIFacilityOut> getFacilityById(@PathVariable("facilityId") String fid) {
	try {
		APIFacilityOut res=Transformer.FacilityFromModel(fci.getFacilityById(fid));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
}



}
