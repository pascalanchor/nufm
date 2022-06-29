package avh.nufm.business.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.FacilityType;
@Repository
public interface FacilityTypeRepo extends CrudRepository<FacilityType, String>{

}
