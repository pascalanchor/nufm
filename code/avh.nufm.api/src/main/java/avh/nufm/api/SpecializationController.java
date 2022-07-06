package avh.nufm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.SpecializationControllerImpl;
import avh.nufm.api.model.Transformer;
import avh.nufm.api.model.in.APISpecializationIn;
import avh.nufm.api.model.out.APISpecializationOut;
import avh.nufm.business.model.Specialization;

@RestController
public class SpecializationController {
@Autowired private SpecializationControllerImpl speci;


@PostMapping("avh/nufm/v1/private/specialization/add")
public ResponseEntity<APISpecializationOut> addSpecialization(
		@RequestBody APISpecializationIn specin){
	
	try {
		Specialization spec=Transformer.specToModel(specin);
		APISpecializationOut res=Transformer.specFromModel(speci.addSpecialization(spec));
		return ResponseEntity.ok().body(res);
		
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}

}
