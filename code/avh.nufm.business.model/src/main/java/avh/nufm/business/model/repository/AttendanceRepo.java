package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Attendance;

@Repository
public interface AttendanceRepo extends CrudRepository<Attendance, String>{

}
