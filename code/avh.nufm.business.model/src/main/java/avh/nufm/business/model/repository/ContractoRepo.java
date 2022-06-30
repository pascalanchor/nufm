package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Contractor;
@Repository
public interface ContractoRepo extends CrudRepository<Contractor, String>{

}
