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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import avh.nufm.api.common.PathCte;
import avh.nufm.api.impl.SafetyMaterialControllerImpl;
import avh.nufm.api.impl.SafetyWorkerControllerImpl;
import avh.nufm.api.impl.services.FileStorageService;
import avh.nufm.api.model.in.APISafetyMaterialIn;
import avh.nufm.api.model.out.APISafetyMaterialOut;
import avh.nufm.api.model.transformer.SafetyMaterialTransformer;
import avh.nufm.business.model.Document;
import avh.nufm.business.model.SafetyMaterial;

@RestController
public class SafetyMaterialController {
@Autowired SafetyMaterialControllerImpl sftimpl;
@Autowired SafetyWorkerControllerImpl sftwrimpl;
@Autowired private FileStorageService fss;


@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PostMapping(PathCte.AddSafetyMaterialServletPath)
public ResponseEntity<APISafetyMaterialOut> addSafetyMaterial(@RequestParam("materialImage") MultipartFile materialImage,@RequestParam("data") String data){
	try {
		APISafetyMaterialOut res;
		//image storage path 
		String path = "images/safety";
		 APISafetyMaterialIn sftin=new ObjectMapper().readValue(data, APISafetyMaterialIn.class);
		SafetyMaterial sft=SafetyMaterialTransformer.sftToModel(sftin);
		//save and assign the image
		String imagePath = fss.storeFile(materialImage, path);
		Document doc = new Document();
		doc.setSafetyMaterialId(sft.getEid());
		doc.setDocumentPath(imagePath);
		sftimpl.addSafetyDocument(doc);
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
public ResponseEntity<APISafetyMaterialOut> updateSafetyMaterials(@RequestParam("materialImage") MultipartFile materialImage,@RequestParam("data") String data,@RequestParam("oldSftId") String oldSftId){
	try {
		APISafetyMaterialOut res;
		String path = "images/safety";
		APISafetyMaterialIn sftin=new ObjectMapper().readValue(data, APISafetyMaterialIn.class);
		SafetyMaterial sft=SafetyMaterialTransformer.sftToModel(sftin);
		//save and assign the image
		String imagePath = fss.storeFile(materialImage, path);
		Document doc = new Document();
		doc.setSafetyMaterialId(sft.getEid());
		doc.setDocumentPath(imagePath);
		sftimpl.addSafetyDocument(doc);
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

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping(PathCte.GetSafetyMaterialsById)
public ResponseEntity<APISafetyMaterialOut> getSafetyMaterialsById(@RequestParam String id){
	try {
		APISafetyMaterialOut res= SafetyMaterialTransformer.sftFromModel(sftimpl.getSafetyMaterialsById(id));
		
			res.setWorkers(sftwrimpl.getWorkersForMaterial(res.getEid()));
		
		return  ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}

}
