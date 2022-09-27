package avh.nufm.business.model.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.ContractType;

@Repository
public interface ContractTypeRepo extends CrudRepository<ContractType, String> {
	public Optional<ContractType> findByEid(String eid);
}
