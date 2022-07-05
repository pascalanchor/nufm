package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Specialization;
@Repository
public interface SpecializationRepo extends CrudRepository<Specialization, String>{
List<Specialization> findByName(String name);
}
