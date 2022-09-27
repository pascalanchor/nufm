package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Document;
import avh.nufm.business.model.Facility;

@Repository
public interface DocumentRepo  extends CrudRepository<Document, String>{
	public List<Document> findByFacility(Facility facility);
}
