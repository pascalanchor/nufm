package avh.nufm.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.common.PathCte;
import avh.nufm.api.impl.SafetyMaterialControllerImpl;
import avh.nufm.api.impl.SafetyWorkerControllerImpl;
import avh.nufm.api.model.in.APISafetyMaterialIn;
import avh.nufm.api.model.out.APISafetyMaterialOut;
import avh.nufm.api.model.transformer.SafetyMaterialTransformer;
import avh.nufm.business.model.SafetyMaterial;

@RestController
public class SafetyMaterialController {
@Autowired SafetyMaterialControllerImpl sftimpl;
@Autowired SafetyWorkerControllerImpl sftwrimpl;



@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PostMapping(PathCte.AddSafetyMaterialServletPath)
public ResponseEntity<APISafetyMaterialOut> addSafetyMaterial(@RequestBody APISafetyMaterialIn sftin){
	try {
		APISafetyMaterialOut res;
		SafetyMaterial sft=SafetyMaterialTransformer.sftToModel(sftin);
		res=SafetyMaterialTransformer.sftFromModel(sftimpl.addSafetyMaterial(sft, sftin.getWorkers()));
		res.setWorkers(sftwrimpl.getWorkersForMaterial(res.getEid()));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}


@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping(PathCte.GetAllSafetyMaterialsServletPath)
public ResponseEntity<List<APISafetyMaterialOut>> getAllSafetyMaterials(){
	try {
		List<APISafetyMaterialOut> res= SafetyMaterialTransformer.listFromItr(sftimpl.getAllSafetyMaterials());
		for(APISafetyMaterialOut itr:res) {
			itr.setWorkers(sftwrimpl.getWorkersForMaterial(itr.getEid()));
		}
		return  ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}


@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PutMapping(PathCte.UpdateSafetyMaterialServletPath)
public ResponseEntity<APISafetyMaterialOut> updateSafetyMaterials(@RequestBody APISafetyMaterialIn sftin,@RequestParam String oldSftId){
	try {
		APISafetyMaterialOut res;
		SafetyMaterial sft=SafetyMaterialTransformer.sftToModel(sftin);
		res=SafetyMaterialTransformer.sftFromModel(sftimpl.updateSafetyMaterial(oldSftId,sft, sftin.getWorkers()));
		res.setWorkers(sftwrimpl.getWorkersForMaterial(res.getEid()));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@DeleteMapping(PathCte.deleteSafetyMaterialServletPath)
public ResponseEntity<Boolean> deleteSafetyMaterial(@RequestParam String sftId){
	try {
		return ResponseEntity.ok().body(sftimpl.deleteSafetyMaterial(sftId));
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}

}
