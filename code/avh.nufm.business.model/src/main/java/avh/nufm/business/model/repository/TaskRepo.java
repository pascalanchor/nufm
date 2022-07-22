package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Task;
@Repository
public interface TaskRepo extends CrudRepository<Task, String>{
List<Task> findByName(String name);
}
