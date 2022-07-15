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
import avh.nufm.api.impl.InvoiceControllerImpl;
import avh.nufm.api.model.in.APIInvoiceIn;
import avh.nufm.api.model.out.APIInvoiceOut;
import avh.nufm.api.model.transformer.InvoiceTransformer;
import avh.nufm.business.model.Invoice;

@RestController
public class InvoiceController {
@Autowired InvoiceControllerImpl invImp;

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PostMapping(PathCte.AddInvoiceServletPath)
public ResponseEntity<APIInvoiceOut> addInvoice(@RequestBody APIInvoiceIn invIn){
	try {
		Invoice inv=InvoiceTransformer.InvoiceToModel(invIn);
		APIInvoiceOut res=InvoiceTransformer.InvoiceFromModel(invImp.addInvoice(inv));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}


@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PutMapping(PathCte.UpdateInvoiceServletPath)
public ResponseEntity<APIInvoiceOut> updateInvoice(@RequestParam String invId,
		@RequestBody APIInvoiceIn invIn){
	try {
		Invoice newinv=InvoiceTransformer.InvoiceToModel(invIn);
		APIInvoiceOut res=InvoiceTransformer.InvoiceFromModel(invImp.updateInvoice(invId, newinv));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@DeleteMapping(PathCte.DeleteInvoiceServletPath)
public ResponseEntity<Boolean> deleteInvoice(@RequestParam String invId){
	try {
		if(invImp.deleteInvoice(invId))
			return ResponseEntity.ok().body(true);
		else
			return ResponseEntity.ok().body(false);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}


@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping(PathCte.GetAllInvoiceServletPath)
public ResponseEntity<List<APIInvoiceOut>> getAllInvoices(){
	try {
		List<APIInvoiceOut> res=InvoiceTransformer.listInvoiceFromIterable(invImp.getAllInvoices());
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}


@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping(PathCte.SearchInvoiceServletPath)
public ResponseEntity<List<APIInvoiceOut>> searchInvoice(@RequestParam String invoiceTo){
	try {
		List<APIInvoiceOut> res=InvoiceTransformer.listInvoiceFromIterable(invImp.searchInvoices(invoiceTo));
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}
}
