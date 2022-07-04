package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.UserSpecialization;
@Repository
public interface UserSpecializationRepo extends CrudRepository<UserSpecialization, String>{

}
