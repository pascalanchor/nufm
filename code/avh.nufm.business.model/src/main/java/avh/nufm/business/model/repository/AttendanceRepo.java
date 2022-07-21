package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Attendance;
import avh.nufm.business.model.NufmUser;

@Repository
public interface AttendanceRepo extends CrudRepository<Attendance, String>{
List<Attendance> findByNufmUser(NufmUser nufmUser);

}
