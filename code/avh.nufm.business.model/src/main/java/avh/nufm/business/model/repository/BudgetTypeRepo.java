package avh.nufm.business.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.BudgetType;

@Repository
public interface BudgetTypeRepo extends CrudRepository<BudgetType, String> {
	public Optional<BudgetType> findByEid(String eid);
	public List<BudgetType> findAll();
}