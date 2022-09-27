package avh.nufm.business.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Contract;

@Repository
public interface ContractRepo extends CrudRepository<Contract, String> {
	public Optional<Contract> findByEid(String eid);
	public List<Contract> findAll();
}
