package avh.nufm.business.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Vendor;

@Repository
public interface VendorRepo extends CrudRepository<Vendor, String> {
	public Optional<Vendor> findById(String id);
	public List<Vendor> findAll();
}
