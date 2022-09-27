package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.api.impl.logic.EInvoiceStatus;
import avh.nufm.business.model.Invoice;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class InvoiceControllerImpl {

	@Autowired private NufmRepos repo;
	
	@Transactional
	public Invoice addInvoice(Invoice inv) {
		//check if missing invoice details 
		if(inv.getDeliverTo()==null || inv.getDeliverTo().equals(""))
			throw new BusinessException("you must enter the deliver to !!");
		if(inv.getInvoiceTo()==null || inv.getInvoiceTo().equals(""))
			throw new BusinessException("you must enter the invoice to !!");
		
		//check the status
		EInvoiceStatus sts=EInvoiceStatus.fromString(inv.getStatus());
		if(sts==null)
			throw new BusinessException(String.format("invalid status format :%s", inv.getStatus()));
		
		//complete the definitions
		inv.setEid(UUID.randomUUID().toString());
		inv.setDate(Timestamp.valueOf(LocalDateTime.now()));
		//now we can save to the db
		repo.getInvrepo().save(inv);
		return inv;
	}
	
	
	public Iterable<Invoice> getAllInvoices()
	{
		return repo.getInvrepo().findAll();
	}
	
	@Transactional
	public Invoice updateInvoice(String id,Invoice newInvoice) {
		if(id==null || id.equals(""))
			throw new BusinessException("you must enter the invoice id !!");
		
		//check the invoice id
		Optional<Invoice> oldInvList =repo.getInvrepo().findById(id);
		if(oldInvList==null || oldInvList.isEmpty())
			throw new BusinessException(String.format("invalid invoice id:%s", id));
		Invoice oldInv=oldInvList.get();
		
		//check the new invoice details
		if(newInvoice.getDeliverTo()==null || newInvoice.getDeliverTo().equals(""))
			throw new BusinessException("you must enter the deliver to !!");
		if(newInvoice.getInvoiceTo()==null || newInvoice.getInvoiceTo().equals(""))
			throw new BusinessException("you must enter the invoice to !!");
		
		
		oldInv.setDeliverTo(newInvoice.getDeliverTo());
		oldInv.setInvoiceTo(newInvoice.getInvoiceTo());
		oldInv.setSubTotal(newInvoice.getSubTotal());
		oldInv.setSalesTax(newInvoice.getSalesTax());
		oldInv.setQuantity(newInvoice.getQuantity());
		oldInv.setPrice(newInvoice.getPrice());
		oldInv.setInvoiceTotal(newInvoice.getInvoiceTotal());
		//oldInv.setDescription(newInvoice.getDescription());
		//oldInv.setDate(newInvoice.getDate());
		//check the status
		EInvoiceStatus sts=EInvoiceStatus.fromString(newInvoice.getStatus());
		if(sts==null)
			throw new BusinessException(String.format("invalid status format :%s", newInvoice.getStatus()));
		
		oldInv.setStatus(newInvoice.getStatus());
		
		
		//save to db
		repo.getInvrepo().save(oldInv);
		return oldInv;
		
		
	}
	
	
	public boolean deleteInvoice(String id) {
		if(id==null || id.equals(""))
			throw new BusinessException("you must enter the invoice id !!");
		//check the invoice id
		Optional<Invoice> invOp=repo.getInvrepo().findById(id);
		if(invOp==null || invOp.isEmpty())
			throw new BusinessException(String.format("invalid invoice id:%s", id));
		//delete the invoice
		repo.getInvrepo().delete(invOp.get());
		//check if deleted
		Optional<Invoice> invTest=repo.getInvrepo().findById(id);
		if(invTest==null || invTest.isEmpty())
			return true;
		else 
			return false;
	}
	
	public Iterable<Invoice> searchInvoices(String text){//we will search on the invoiceTo only
		
		List<Invoice> res=new ArrayList<Invoice>();
		for(Invoice itr:repo.getInvrepo().findAll()) {
			if(itr.getInvoiceTo().equals(text))
				res.add(itr);
		}
		
		return res;
			
	}


	public Invoice getInvoiceById(String id) {
		if(id==null || id.equals(""))
			throw new BusinessException("you must enter the invoice id !!");
		
		//check the invoice id
		Optional<Invoice> oldInvList =repo.getInvrepo().findById(id);
		if(oldInvList==null || oldInvList.isEmpty())
			throw new BusinessException(String.format("invalid invoice id:%s", id));
		Invoice oldInv=oldInvList.get();
		
		return oldInv;
	}
	
}
