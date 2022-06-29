package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.UserRole;
@Repository
public interface UserRoleRepo extends CrudRepository<UserRole, String>{

}
