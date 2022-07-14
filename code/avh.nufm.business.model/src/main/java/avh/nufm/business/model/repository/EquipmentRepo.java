package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Equipment;
@Repository
public interface EquipmentRepo extends CrudRepository<Equipment, String>{
}
