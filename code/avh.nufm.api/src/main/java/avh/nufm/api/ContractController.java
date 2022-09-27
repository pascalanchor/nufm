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
import avh.nufm.api.impl.ContractControllerImpl;
import avh.nufm.api.impl.services.FileStorageService;
import avh.nufm.api.model.in.APIContractIn;
import avh.nufm.api.model.out.APIContractOut;
import avh.nufm.api.model.transformer.ContractTransformer;
import avh.nufm.business.model.Contract;

@RestController
public class ContractController {
	@Autowired
	ContractControllerImpl contractConImpl;
	@Autowired
	private FileStorageService fileStorSer;

	// ADD CONTRACT
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping(PathCte.AddContractServletPath)
	public ResponseEntity<APIContractOut> createContract(@RequestParam("data") String data,
			@RequestParam("contractDocs") List<MultipartFile> contractDocs) {
		try {
			String fpath = "images/docs";
			APIContractIn contractIn = new ObjectMapper().readValue(data, APIContractIn.class);
			Contract contractModel = ContractTransformer.ContractToModel(contractIn);
			contractModel = contractConImpl.addContract(contractModel, contractIn.getType());
			contractConImpl.addContractDocs(contractModel.getEid(), fileStorSer.storeMultipleFile(contractDocs, fpath));
			return ResponseEntity.ok().body(ContractTransformer.ContractFromModel(contractModel));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// DELETE CONTRACT
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@DeleteMapping(PathCte.DeleteContractServletPath + "/{id}")
	public ResponseEntity<String> deleteContract(@PathVariable("id") String id) {
		try {
			contractConImpl.deleteContract(id);
			return ResponseEntity.ok().body("Contract has been deleted successfully");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// GET CONTRACTS
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR','WORKER','OCCUPANT')")
	@GetMapping(PathCte.GetAllContractsServletPath)
	public ResponseEntity<List<APIContractOut>> getContracts() {
		try {
			List<Contract> contracts = contractConImpl.getContracts();
			List<APIContractOut> res = new ArrayList<>();
			contracts.stream().forEach(e -> res.add(ContractTransformer.ContractFromModel(e)));
			return ResponseEntity.ok().body(res);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// GET CONTRACT BY ID
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@GetMapping(PathCte.GetContractByIdServletPath + "/{id}")
	public ResponseEntity<APIContractOut> getContractById(@PathVariable("id") String id) {
		try {
			Contract res = contractConImpl.getContractById(id);
			return ResponseEntity.ok().body(ContractTransformer.ContractFromModel(res));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// UPDATE CONTRACT
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PutMapping(PathCte.UpdateContractServletPath)
	public ResponseEntity<APIContractOut> updateContract(@RequestParam("data") String data,
			@RequestParam("contractDocs") List<MultipartFile> contractDocs, @RequestParam("contractId") String id) {
		try {
			String fpath = "images/docs";
			APIContractIn contractIn = new ObjectMapper().readValue(data, APIContractIn.class);
			Contract contractModel = ContractTransformer.ContractToModel(contractIn);
			contractModel = contractConImpl.updateContract(id,contractModel, contractIn.getType());
			contractConImpl.addContractDocs(contractModel.getEid(), fileStorSer.storeMultipleFile(contractDocs, fpath));
			return ResponseEntity.ok().body(ContractTransformer.ContractFromModel(contractModel));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
		}
	}
}
