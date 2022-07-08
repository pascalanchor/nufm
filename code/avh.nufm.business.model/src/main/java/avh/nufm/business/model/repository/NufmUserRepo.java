package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.NufmUser;
@Repository
public interface NufmUserRepo extends CrudRepository<NufmUser, String>{
	public NufmUser findByEid(String eid);
	List<NufmUser> findByFullName(String fullName);
}
