package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Specialization;
@Repository
public interface SpecializationRepo extends CrudRepository<Specialization, String>{

}
