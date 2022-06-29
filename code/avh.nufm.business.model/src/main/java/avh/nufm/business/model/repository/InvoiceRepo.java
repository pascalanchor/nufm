package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Invoice;
@Repository
public interface InvoiceRepo extends CrudRepository<Invoice, String>{

}
