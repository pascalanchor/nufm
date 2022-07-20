package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Facility;
import avh.nufm.business.model.FacilityDocument;

@Repository
public interface FacilityDocumentRepo  extends CrudRepository<FacilityDocument, String>{
	public List<FacilityDocument> findByFacility(Facility facility);
}
