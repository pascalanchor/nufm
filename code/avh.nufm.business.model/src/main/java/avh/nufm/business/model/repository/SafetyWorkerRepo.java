package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.SafetyWorker;
@Repository
public interface SafetyWorkerRepo extends CrudRepository<SafetyWorker, String>{

}
