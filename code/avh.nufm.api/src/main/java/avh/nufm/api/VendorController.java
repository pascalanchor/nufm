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
import avh.nufm.api.impl.VendorControllerImpl;
import avh.nufm.api.impl.services.FileStorageService;
import avh.nufm.api.model.in.APIVendorIn;
import avh.nufm.api.model.out.APIVendorOut;
import avh.nufm.api.model.transformer.VendorTransformer;
import avh.nufm.business.model.Vendor;

@RestController
public class VendorController {
	@Autowired
	VendorControllerImpl vendorConImpl;
	@Autowired
	private FileStorageService fileStorSer;

	// ADD VENDOR
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping(PathCte.AddVendorServletPath)
	public ResponseEntity<APIVendorOut> createVendor(@RequestParam("data") String data,
			@RequestParam("vendorDocs") List<MultipartFile> vendorDocs) {
		try {
			String fpath = "images/docs";
			APIVendorIn vendorIn = new ObjectMapper().readValue(data, APIVendorIn.class);
			Vendor vendorModel = VendorTransformer.VendorToModel(vendorIn);
			vendorModel = vendorConImpl.addVendor(vendorModel);
			vendorConImpl.addVendorDocs(vendorModel.getEid(), fileStorSer.storeMultipleFile(vendorDocs, fpath));
			return ResponseEntity.ok().body(VendorTransformer.VendorFromModel(vendorModel));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// DELETE VENDOR
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@DeleteMapping(PathCte.DeleteVendorServletPath + "/{id}")
	public ResponseEntity<String> deleteVendor(@PathVariable("id") String id) {
		try {
			vendorConImpl.deleteVendor(id);
			return ResponseEntity.ok().body("Vendor has been deleted successfully");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// GET VENDORS
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR','WORKER','OCCUPANT')")
	@GetMapping(PathCte.GetAllVendorsServletPath)
	public ResponseEntity<List<APIVendorOut>> getVendors() {
		try {
			List<Vendor> vendors = vendorConImpl.getVendors();
			List<APIVendorOut> res = new ArrayList<>();
			vendors.stream().forEach(e -> res.add(VendorTransformer.VendorFromModel(e)));
			return ResponseEntity.ok().body(res);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// GET VENDOR BY ID
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@GetMapping(PathCte.GetVendorByIdServletPath + "/{id}")
	public ResponseEntity<APIVendorOut> getVendorById(@PathVariable("id") String id) {
		try {
			Vendor res = vendorConImpl.getVendorById(id);
			return ResponseEntity.ok().body(VendorTransformer.VendorFromModel(res));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// UPDATE VENDOR
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PutMapping(PathCte.UpdateVendorServletPath)
	public ResponseEntity<APIVendorOut> updateVendor(@RequestParam("data") String data, @RequestParam("vendorId") String id,
			@RequestParam("vendorDocs") List<MultipartFile> vendorDocs) {
		try {
			String fpath = "images/docs";
			APIVendorIn vendorIn = new ObjectMapper().readValue(data, APIVendorIn.class);
			Vendor vendorModel = VendorTransformer.VendorToModel(vendorIn);
			vendorModel = vendorConImpl.updateVendor(id,vendorModel);
			vendorConImpl.addVendorDocs(vendorModel.getEid(), fileStorSer.storeMultipleFile(vendorDocs, fpath));
			return ResponseEntity.ok().body(VendorTransformer.VendorFromModel(vendorModel));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
		}
	}
}
