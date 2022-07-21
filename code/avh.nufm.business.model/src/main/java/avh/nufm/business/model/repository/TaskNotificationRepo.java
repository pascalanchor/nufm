package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskNotificationRepo extends CrudRepository<TaskNotificationRepo, String>{

}
