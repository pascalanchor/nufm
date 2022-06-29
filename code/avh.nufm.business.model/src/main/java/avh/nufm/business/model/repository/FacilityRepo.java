package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Facility;
@Repository
public interface FacilityRepo extends CrudRepository<Facility, String>{

}
