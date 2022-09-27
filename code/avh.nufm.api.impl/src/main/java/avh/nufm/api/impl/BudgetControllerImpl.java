package avh.nufm.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Budget;
import avh.nufm.business.model.BudgetType;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class BudgetControllerImpl {
	@Autowired
	private NufmRepos repo;

	@Transactional
	public Budget getBudgetById(String budgetId) {
		Optional<Budget> res = repo.getBudgetRepo().findById(budgetId);
		if (res.isPresent()) {
			return res.get();
		} else
			throw new BusinessException(String.format("budget does not exist"));
	}

	@Transactional
	public List<Budget> getBudgets() {
		List<Budget> budgets = repo.getBudgetRepo().findAll();
		return budgets;
	}

	@Transactional
	public void deleteBudget(String budgetId) {
		Optional<Budget> res = repo.getBudgetRepo().findById(budgetId);
		repo.getBudgetRepo().delete(res.get());
	}

	@Transactional
	public Budget addBudget(Budget budget,String facilityId, String typeId) {
		budget.setIid(UUID.randomUUID().toString());
		Optional<Facility> facility = repo.getFacrepo().findById(facilityId);
		if (facility.isPresent()) {
			budget.setFacility(facility.get());
		} else {
			throw new BusinessException(String.format("facility does not exist"));
		}
		Optional<BudgetType> budgetType = repo.getBudgetTypeRepo().findById(typeId);
		if (budgetType.isPresent()) {
			budget.setBudgetType(budgetType.get());
		} else {
			throw new BusinessException(String.format("Budget type does not exist"));
		}
		repo.getBudgetRepo().save(budget);
		return budget;
	}

	public Budget updateBudget(String id, Budget budgetModel, String facilityId, String typeId) {
		Optional<Budget> oldBudget = repo.getBudgetRepo().findById(id);
		if (oldBudget.isEmpty()) {
			throw new BusinessException(String.format("budget does not exist"));
		}
		Budget res = oldBudget.get();
		Optional<Facility> facility = repo.getFacrepo().findById(facilityId);
		if (facility.isPresent()) {
			res.setFacility(facility.get());
		} else {
			throw new BusinessException(String.format("facility does not exist"));
		}
		Optional<BudgetType> budgetType = repo.getBudgetTypeRepo().findById(typeId);
		if (budgetType.isPresent()) {
			res.setBudgetType(budgetType.get());
		} else {
			throw new BusinessException(String.format("Budget type does not exist"));
		}
		res.setDifference(budgetModel.getDifference());
		res.setEstimation(budgetModel.getEstimation());
		res.setIncomeTax(budgetModel.getIncomeTax());
		res.setMonth(budgetModel.getMonth());
		res.setName(budgetModel.getName());
		res.setSubmissionDate(budgetModel.getSubmissionDate());
		res.setYear(budgetModel.getYear());
		repo.getBudgetRepo().save(res);
		return res;	
	}
}
