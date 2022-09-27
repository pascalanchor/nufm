package avh.nufm.business.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Facility;
@Repository
public interface FacilityRepo extends CrudRepository<Facility, String>{
	List<Facility> findByName(String name);
	public Optional<Facility> findByEid(String eid);
}
