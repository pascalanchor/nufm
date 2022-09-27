package avh.nufm.business.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Budget;

@Repository
public interface BudgetRepo extends CrudRepository<Budget, String> {
	public Optional<Budget> findById(String id);
	public List<Budget> findAll();
}
