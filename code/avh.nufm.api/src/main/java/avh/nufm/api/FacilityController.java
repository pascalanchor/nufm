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

import avh.nufm.api.email.EmailBuilder;
import avh.nufm.api.email.EmailService;
import avh.nufm.api.impl.ConfirmationTokenControllerImpl;
import avh.nufm.api.impl.FacilityControllerImpl;
import avh.nufm.api.impl.OccupantControllerImpl;
import avh.nufm.api.impl.UserControllerImpl;
//import avh.nufm.api.model.Transformer;
import avh.nufm.api.model.in.APIFacilityIn;
import avh.nufm.api.model.out.APIFacilityOut;
import avh.nufm.api.model.transformer.FacilityTransformer;
import avh.nufm.api.model.transformer.OccupantTransformer;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.NufmUser;
import avh.nufm.security.common.SecurityCte;

@RestController
public class FacilityController {
	@Autowired private FacilityControllerImpl fci;
	@Autowired UserControllerImpl ucImpl;
	@Autowired OccupantControllerImpl ocImpl;
	@Autowired EmailService emailSender;
	@Autowired private EmailBuilder emailBuilder;
	@Autowired private ConfirmationTokenControllerImpl ctImpl;
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping("avh/nufm/v1/private/facility/add")//only administrator and owner can define new facility
	public ResponseEntity<APIFacilityOut> createFacility(@RequestBody APIFacilityIn fc) {
	try {
		//1-create the facility
		Facility fct= FacilityTransformer.FacilityToModel(fc);
		fct = fci.createFacility(fct);
		System.out.println(fct.getEid());
		APIFacilityOut res=FacilityTransformer.FacilityFromModel(fct);
		//2-add equipments to facility
		fci.addEquipmentToFacility(fct.getEid(), fc.getEquipments());
		 // create and send confirmation token to user
		//3-create the occupant
		NufmUser occupant = ocImpl.occupantExists(fc.getOccupant().getEmail());
		if(occupant == null)
		{NufmUser occupantModel = OccupantTransformer.ModelFromOccupant(fc.getOccupant());
		String pwd = ocImpl.createOccupant(occupantModel);
		ucImpl.addRoleToUser(fc.getOccupant().getEmail(), SecurityCte.RoleOccupant);
		String tok = ctImpl.createConfirmationToken(fc.getOccupant().getEmail());
		String link = "http://localhost:6338"+SecurityCte.PublicServletPath+"/register/confirm/" + tok;
		String mail = emailBuilder.confirmOccupant(fc.getOccupant().getFullName(),fc.getOccupant().getEmail(),pwd,link);
		emailSender.send(fc.getOccupant().getEmail(), mail);
		fci.addOccupantToFacility(fct.getEid(),occupantModel.getEid());
        res.setOccupantName(occupantModel.getFullName());}
		fci.addOccupantToFacility(fct.getEid(), fc.getOccupant().getEmail());
		res.setOccupantName(fc.getOccupant().getFullName());
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
	}
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PutMapping("avh/nufm/v1/private/facility/update")//only administrator and owner can define new facility
public ResponseEntity<APIFacilityOut> updateFaclity(@RequestParam String facility_id, @RequestBody APIFacilityIn fc) {
	
	try {
		//1-update the facility
		Facility facility = fci.updateFacility(facility_id, FacilityTransformer.FacilityToModel(fc));
		//2-add equipments to facility
		fci.updateEquipmentOfFacility(facility_id, fc.getEquipments());
		//3-update occupant and create the res
		NufmUser occupant = ocImpl.occupantExists(fc.getOccupant().getEmail());
		APIFacilityOut res = FacilityTransformer.FacilityFromModel(facility);
		if(occupant == null)
		{NufmUser occupantModel = OccupantTransformer.ModelFromOccupant(fc.getOccupant());
		String pwd = ocImpl.createOccupant(occupantModel);
		ucImpl.addRoleToUser(fc.getOccupant().getEmail(), SecurityCte.RoleOccupant);
		String tok = ctImpl.createConfirmationToken(fc.getOccupant().getEmail());
		String link = "http://localhost:6338"+SecurityCte.PublicServletPath+"/register/confirm/" + tok;
		String mail = emailBuilder.confirmOccupant(fc.getOccupant().getFullName(),fc.getOccupant().getEmail(),pwd,link);
		emailSender.send(fc.getOccupant().getEmail(), mail);
		fci.addOccupantToFacility(facility_id,occupantModel.getEid());
        res.setOccupantName(occupantModel.getFullName());}
		fci.addOccupantToFacility(facility_id, fc.getOccupant().getEmail());
		res.setOccupantName(fc.getOccupant().getFullName());
		return ResponseEntity.ok().body(res);
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
