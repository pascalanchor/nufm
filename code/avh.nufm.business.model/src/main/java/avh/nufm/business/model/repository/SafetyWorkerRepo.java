package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.SafetyMaterial;
import avh.nufm.business.model.SafetyWorker;
@Repository
public interface SafetyWorkerRepo extends CrudRepository<SafetyWorker, String>{
List<SafetyWorker> findBySafetyMaterial(SafetyMaterial safetyMaterial);
}
