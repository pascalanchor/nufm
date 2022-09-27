package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Document;

@Repository
public interface DocumentRepo  extends CrudRepository<Document, String>{
	public List<Document> findByFacilityId(String facilityId);
	public List<Document> findByVendorId(String vendorId);
	public List<Document> findByContractId(String contractId);
	public List<Document> findByEquipmentId(String equipmentId);
}
