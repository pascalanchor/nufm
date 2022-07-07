package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserSpecialization;
@Repository
public interface UserSpecializationRepo extends CrudRepository<UserSpecialization, String>{
	public List<UserSpecialization> findByNufmUser(NufmUser nufmUser);
}
