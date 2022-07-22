package avh.nufm.api;

import java.util.ArrayList;
import java.util.List;

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
import avh.nufm.api.impl.ConfirmationTokenControllerImpl;
import avh.nufm.api.impl.FacilityControllerImpl;
import avh.nufm.api.impl.OccupantControllerImpl;
import avh.nufm.api.impl.UserControllerImpl;
import avh.nufm.api.impl.services.FileStorageService;
import avh.nufm.api.model.in.APIFacilityIn;
import avh.nufm.api.model.in.APIOccupantIn;
import avh.nufm.api.model.out.APIFacilityOut;
import avh.nufm.api.model.out.APIOccupantOut;
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
	@Autowired private FileStorageService fss;
	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping(PathCte.AddFacilityServletPath)//only administrator and owner can define new facility
	public ResponseEntity<APIFacilityOut> createFacility(@RequestParam("profileImage") MultipartFile profileImage,
			@RequestParam("facilityDoc1") MultipartFile facilityDoc1,
			@RequestParam("facilityData") String facilityData,
			@RequestParam("occupantData") String occupantData) {
	try {
		//image storage path 
		String ipath = "D:\\AVH projects\\Workspaces\\NufmWorkspace\\nufm\\code\\avh.nufm\\src\\main\\resources\\storage\\profile\\occupant";
		//facility docs path
		String fpath = "D:\\AVH projects\\Workspaces\\NufmWorkspace\\nufm\\code\\avh.nufm\\src\\main\\resources\\storage\\facility\\docs";
		APIOccupantIn occupantIn = new ObjectMapper().readValue(occupantData, APIOccupantIn.class);	
		APIFacilityIn fc = new ObjectMapper().readValue(facilityData, APIFacilityIn.class);
		//1-create the facility
		Facility fct= FacilityTransformer.FacilityToModel(fc);
		fct = fci.createFacility(fct);
		fci.addFacilityDoc(fct.getEid(), fss.storeFile(facilityDoc1, fpath));
		//add occupant profile photo
		String imagePath = fss.storeFile(profileImage, ipath);	
		APIFacilityOut res=FacilityTransformer.FacilityFromModel(fct);
		//2-add equipments to facility
		fci.addEquipmentToFacility(fct.getEid(), fc.getEquipments());
		 // create and send confirmation token to user
		//3-create the occupant
		NufmUser occupant = ocImpl.occupantExists(occupantIn.getEmail());
		if(occupant == null)
		{NufmUser occupantModel = OccupantTransformer.ModelFromOccupant(occupantIn);
		String pwd = ocImpl.createOccupant(occupantModel,imagePath);
		ucImpl.addRoleToUser(occupantIn.getEmail(), SecurityCte.RoleOccupant);
		String tok = ctImpl.createConfirmationToken(occupantIn.getEmail());
		String link = "http://localhost:6338"+SecurityCte.PublicServletPath+"/register/confirm/" + tok;
		String mail = emailBuilder.confirmOccupant(occupantIn.getFullName(),occupantIn.getEmail(),pwd,link);
		emailSender.send(occupantIn.getEmail(), mail);
		fci.addOccupantToFacility(fct.getEid(),occupantModel.getEid());
		APIOccupantOut occupantOut = OccupantTransformer.OccupantFromModel(occupantModel);
		occupantOut.setProfileImage(imagePath);
        res.setOccupant(occupantOut);}
		else {
		APIOccupantOut occupantOut = OccupantTransformer.OccupantFromModel(occupant);
		occupantOut.setProfileImage(imagePath);
		fci.addOccupantToFacility(fct.getEid(), occupantIn.getEmail());
		res.setOccupant(occupantOut);}
		List<String> docs = fci.getFacilityDocuments(fct.getEid());
		res.setDocs(docs);
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
	}
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PutMapping(PathCte.EditFacilityServletPath)//only administrator and owner can define new facility
public ResponseEntity<APIFacilityOut> updateFaclity(@RequestParam("profileImage") MultipartFile profileImage,
		@RequestParam("facilityId") String id,
		@RequestParam("facilityDoc1") MultipartFile facilityDoc1,
		@RequestParam("facilityData") String facilityData,
		@RequestParam("occupantData") String occupantData) {
	
	try {
		//image storage path 
		String ipath = "D:\\AVH projects\\Workspaces\\NufmWorkspace\\nufm\\code\\avh.nufm\\src\\main\\resources\\storage\\profile\\occupant";
		//facility docs path
		String fpath = "D:\\AVH projects\\Workspaces\\NufmWorkspace\\nufm\\code\\avh.nufm\\src\\main\\resources\\storage\\facility\\docs";
		APIOccupantIn occupantIn = new ObjectMapper().readValue(occupantData, APIOccupantIn.class);	
		APIFacilityIn fc = new ObjectMapper().readValue(facilityData, APIFacilityIn.class);
		//1-create the facility
		Facility fct= FacilityTransformer.FacilityToModel(fc);
		fct = fci.updateFacility(id,fct);
		fci.addFacilityDoc(fct.getEid(), fss.storeFile(facilityDoc1, fpath));
		//add occupant profile photo
		String imagePath = fss.storeFile(profileImage, ipath);	
		APIFacilityOut res=FacilityTransformer.FacilityFromModel(fct);
		//2-add equipments to facility
		fci.addEquipmentToFacility(fct.getEid(), fc.getEquipments());
		//3-update the occupant
        NufmUser occupantModel = OccupantTransformer.ModelFromOccupant(occupantIn);
		ocImpl.updateOccupant(occupantModel,imagePath);
		APIOccupantOut occupantOut = OccupantTransformer.OccupantFromModel(occupantModel);
		occupantOut.setProfileImage(imagePath);
        res.setOccupant(occupantOut);
		List<String> docs = fci.getFacilityDocuments(fct.getEid());
		res.setDocs(docs);
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
	}
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping(PathCte.GetFacilityByIdServletPath+"/{facilityId}")
public ResponseEntity<APIFacilityOut> getFacilityById(@PathVariable("facilityId") String fid) {
	try {
		Facility facility = fci.getFacilityById(fid);
		APIFacilityOut res= FacilityTransformer.FacilityFromModel(fci.getFacilityById(fid));
		res.setOccupant(OccupantTransformer.OccupantFromModel(facility.getNufmUser()));
		res.setDocs(fci.getFacilityDocuments(fid));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
	}
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping(PathCte.GetFacilitiesServletPath)
public ResponseEntity<List<APIFacilityOut>> getFacilities() {
	try {
		List<APIFacilityOut> res = new ArrayList<>();
		List<Facility> fList = fci.getAllFacilities();
		fList.stream().forEach((e)->{
		APIFacilityOut facilityOut = FacilityTransformer.FacilityFromModel(e);
		facilityOut.setOccupant(OccupantTransformer.OccupantFromModel(e.getNufmUser()));
		facilityOut.setDocs(fci.getFacilityDocuments(e.getEid()));
		res.add(facilityOut);
		});
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
	}
}
@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@DeleteMapping(PathCte.DeleteFacilityServletPath+"/{id}")
public ResponseEntity<String> deleteFacility(@PathVariable("id") String id){
	try {
		fci.deleteFacility(id);
		return ResponseEntity.ok().body("facility was deleted successfully");
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
	}
}


}
