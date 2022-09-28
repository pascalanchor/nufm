package avh.nufm.business.model.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.FacilityEquipment;

@Repository
public interface FacilityEquipmentRepo extends CrudRepository<FacilityEquipment, String>{
	public List<FacilityEquipment> findByFacilityId(String eid);

}
