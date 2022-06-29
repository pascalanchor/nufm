package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.TaskNotification;
@Repository
public interface TaskNotificationRepo extends CrudRepository<TaskNotification, String>{

}
