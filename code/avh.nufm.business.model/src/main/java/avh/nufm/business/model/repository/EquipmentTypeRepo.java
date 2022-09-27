package avh.nufm.business.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.EquipmentType;

@Repository
public interface EquipmentTypeRepo extends CrudRepository<EquipmentType, String> {
	public Optional<EquipmentType> findByEid(String eid);  
}
