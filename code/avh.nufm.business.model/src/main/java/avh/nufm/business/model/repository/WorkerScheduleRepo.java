package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.WorkerSchedule;
@Repository
public interface WorkerScheduleRepo extends CrudRepository<WorkerSchedule, String>{

}
