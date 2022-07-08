package avh.nufm.api;

import java.util.ArrayList;
import java.util.List;

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
//import avh.nufm.api.model.Transformer;
import avh.nufm.api.model.in.APIFacilityIn;
import avh.nufm.api.model.out.APIFacilityOut;
import avh.nufm.api.model.transformer.FacilityTransformer;
import avh.nufm.business.model.Facility;

@RestController
public class FacilityController {
@Autowired private FacilityControllerImpl fci;

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PostMapping("avh/nufm/v1/private/facility/add")//only administrator and owner can define new facility
public ResponseEntity<APIFacilityOut> createFacility(@RequestBody APIFacilityIn fc) {
	try {
		Facility fct= FacilityTransformer.FacilityToModel(fc);
		APIFacilityOut res=FacilityTransformer.FacilityFromModel(fci.createFacility(fct));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PutMapping("avh/nufm/v1/private/facility/update")//only administrator and owner can define new facility
public ResponseEntity<APIFacilityOut> updateFaclity(@RequestParam String facility_id, @RequestBody APIFacilityIn fc) {
	
	try {
		Facility fct= FacilityTransformer.FacilityToModel(fc);
		APIFacilityOut fco=FacilityTransformer.FacilityFromModel(fci.updateFacility(facility_id, fct));
		return ResponseEntity.ok().body(fco);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}	
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping("avh/nufm/v1/private/facilities/{facilityId}")
public ResponseEntity<APIFacilityOut> getFacilityById(@PathVariable("facilityId") String fid) {
	try {
		APIFacilityOut res= FacilityTransformer.FacilityFromModel(fci.getFacilityById(fid));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping("avh/nufm/v1/private/facilities")
public ResponseEntity<List<APIFacilityOut>> getFacilities() {
	try {
		List<APIFacilityOut> res = new ArrayList<>();
		List<Facility> fList = fci.getAllFacilities();
		fList.stream().forEach(e-> res.add(FacilityTransformer.FacilityFromModel(e)));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
}



}
