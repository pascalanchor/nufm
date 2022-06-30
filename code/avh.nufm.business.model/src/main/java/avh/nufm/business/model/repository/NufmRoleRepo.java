package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.NufmRole;
@Repository
public interface NufmRoleRepo extends CrudRepository<NufmRole, String>{

}
