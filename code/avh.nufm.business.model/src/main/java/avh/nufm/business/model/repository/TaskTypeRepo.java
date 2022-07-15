package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.TaskType;
@Repository
public interface TaskTypeRepo extends CrudRepository<TaskType, String>{
List<TaskType> findByName(String name);
}
