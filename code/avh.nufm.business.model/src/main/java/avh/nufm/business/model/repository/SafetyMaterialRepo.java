package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.SafetyMaterial;
@Repository
public interface SafetyMaterialRepo extends CrudRepository<SafetyMaterial, String>{

	public SafetyMaterial findByEid(String eid);
}
