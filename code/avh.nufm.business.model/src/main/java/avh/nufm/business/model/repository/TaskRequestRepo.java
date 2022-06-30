package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.TaskRequest;
@Repository
public interface TaskRequestRepo extends CrudRepository<TaskRequest, String>{

}
