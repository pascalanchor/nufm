package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Task;
import avh.nufm.business.model.WorkerTask;
@Repository
public interface WorkerTaskRepo extends CrudRepository<WorkerTask, String>{
List<WorkerTask> findByTask(Task task);
}
