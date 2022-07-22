package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;

import avh.nufm.api.model.in.APIInvoiceIn;
import avh.nufm.api.model.out.APIInvoiceOut;
import avh.nufm.business.model.Invoice;

public class InvoiceTransformer {

	public static APIInvoiceOut InvoiceFromModel(Invoice inv) {
		APIInvoiceOut res=new APIInvoiceOut();
		res.setDeliverTo(inv.getDeliverTo());
		res.setInvoiceTo(inv.getInvoiceTo());
		res.setCreatedAt(inv.getDate());
		res.setQuantity(inv.getQuantity());
		res.setPrice(inv.getPrice());
		res.setSubTotal(inv.getSubTotal());
		res.setSalesTax(inv.getSalesTax());
		res.setInvoiceTotal(inv.getInvoiceTotal());
		res.setDescription(inv.getDescription());
		res.setStatus(inv.getStatus());
		res.setEid(inv.getEid());
		return res;
		
	}
	
	
	public static Invoice InvoiceToModel(APIInvoiceIn invIn) {
		Invoice res=new Invoice();
		res.setDeliverTo(invIn.getDeliverTo());
		res.setInvoiceTo(invIn.getInvoiceTo());
		res.setSubTotal(invIn.getSubTotal());
		res.setSalesTax(invIn.getSalesTax());
		res.setQuantity(invIn.getQuantity());
		res.setPrice(invIn.getPrice());
		res.setInvoiceTotal(invIn.getInvoiceTotal());
		res.setDescription(invIn.getDescription());
		res.setStatus(invIn.getStatus());
		return res;
	}
	
	
	public static List<APIInvoiceOut> listInvoiceFromIterable(Iterable<Invoice> invItr){
		List<APIInvoiceOut> res=new ArrayList<APIInvoiceOut>();
		for(Invoice itr:invItr) {
			res.add(InvoiceFromModel(itr));
		}
		
		return res;
	}
}
