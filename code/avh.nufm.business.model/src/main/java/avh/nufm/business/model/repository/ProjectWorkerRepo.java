package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.ProjectWorker;
@Repository
public interface ProjectWorkerRepo extends CrudRepository<ProjectWorker, String>{

}
