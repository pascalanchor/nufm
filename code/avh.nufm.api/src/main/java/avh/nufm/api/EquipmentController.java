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
import avh.nufm.api.impl.EquipmentControllerImpl;
import avh.nufm.api.impl.services.FileStorageService;
import avh.nufm.api.model.in.APIEquipmentIn;
import avh.nufm.api.model.out.APIEquipmentOut;
import avh.nufm.api.model.transformer.EquipmentTransformer;
import avh.nufm.business.model.Equipment;

@RestController
public class EquipmentController {
	@Autowired
	EquipmentControllerImpl equipmentConImpl;
	@Autowired
	private FileStorageService fileStorSer;
	
	// ADD EQUIPMENT
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping(PathCte.AddEquipmentServletPath)
	public ResponseEntity<APIEquipmentOut> createEquipment(@RequestParam("data") String data,@RequestParam("equipmentDocs") List<MultipartFile> equipmentDocs) {
		try {
			String fpath = "images/docs";
			APIEquipmentIn equipmentIn = new ObjectMapper().readValue(data, APIEquipmentIn.class);
			Equipment equipmentModel = EquipmentTransformer.EquipmentToModel(equipmentIn);
			equipmentModel = equipmentConImpl.addEquipment(equipmentModel,equipmentIn.getTypeId(),equipmentIn.getVendorId());
			equipmentConImpl.addEquipmentDocs(equipmentModel.getEid(), fileStorSer.storeMultipleFile(equipmentDocs, fpath));
			return ResponseEntity.ok().body(EquipmentTransformer.EquipmentFromModel(equipmentModel));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// DELETE EQUIPMENT
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@DeleteMapping(PathCte.DeleteEquipmentServletPath + "/{id}")
	public ResponseEntity<String> deleteEquipment(@PathVariable("id") String id) {
		try {
			equipmentConImpl.deleteEquipment(id);
			return ResponseEntity.ok().body("Equipment has been deleted successfully");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// GET EQUIPMENTS
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR','WORKER','OCCUPANT')")
	@GetMapping(PathCte.GetAllEquipmentsServletPath)
	public ResponseEntity<List<APIEquipmentOut>> getEquipments() {
		try {
			List<Equipment> equipments = equipmentConImpl.getEquipments();
			List<APIEquipmentOut> res = new ArrayList<>();
			equipments.stream().forEach(e -> res.add(EquipmentTransformer.EquipmentFromModel(e)));
			return ResponseEntity.ok().body(res);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// GET EQUIPMENT BY ID
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@GetMapping(PathCte.GetEquipmentByIdServletPath + "/{id}")
	public ResponseEntity<APIEquipmentOut> getEquipmentById(@PathVariable("id") String id) {
		try {
			Equipment res = equipmentConImpl.getEquipmentById(id);
			return ResponseEntity.ok().body(EquipmentTransformer.EquipmentFromModel(res));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// UPDATE EQUIPMENT
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PutMapping(PathCte.UpdateEquipmentServletPath)
	public ResponseEntity<String> updateEquipment(@RequestParam("data") String data) {
		try {

			return ResponseEntity.ok().body("Equipment is updated");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
		}
	}
}
