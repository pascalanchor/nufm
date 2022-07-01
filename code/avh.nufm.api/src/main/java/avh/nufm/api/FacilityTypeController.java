package avh.nufm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.FacilityTypeControllerImpl;
import avh.nufm.api.model.Transformer;
import avh.nufm.api.model.in.FacilityTypeIn;
import avh.nufm.api.model.out.FacilityTypeOut;
import avh.nufm.business.model.FacilityType;

@RestController
public class FacilityTypeController {
	@Autowired private FacilityTypeControllerImpl fcti;
	
	@PostMapping("avh/nufm/v1/private/facilityType/add")
	public ResponseEntity<FacilityTypeOut> addFacilityType(@RequestBody FacilityTypeIn fctin){
		try
		{
			System.out.print("request recieved-----------------------"); 
			FacilityType factype=Transformer.FacTypeToModel(fctin);
			FacilityTypeOut factOut=Transformer.FacTypeFromModel(fcti.addFacType(factype));
			factOut.setName("fuck it");
			return ResponseEntity.ok().body(factOut);
			
		}catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}
	
	@GetMapping("avh/nufm/v1/private/facilityTypes/{typeId}")
	public ResponseEntity<FacilityTypeOut> getFacilityTypeById(@PathVariable("typeId") String typeId) {
		try {
			FacilityType fct = fcti.getById(typeId);
			FacilityTypeOut fctout = Transformer.FacTypeFromModel(fct);
			return ResponseEntity.ok().body(fctout);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

}
