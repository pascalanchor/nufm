package avh.nufm.business.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Equipment;

@Repository
public interface EquipmentRepo extends CrudRepository<Equipment, String>{
	public Optional<Equipment> findById(String id);
	public List<Equipment> findAll();
}
