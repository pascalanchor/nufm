package avh.nufm.api.model.transformer;

import avh.nufm.api.model.in.APIBudgetIn;
import avh.nufm.api.model.out.APIBudgetOut;
import avh.nufm.business.model.Budget;

public class BudgetTransformer {
	public static Budget BudgetToModel(APIBudgetIn bin) {
		Budget res = new Budget();
		res.setName(bin.getName());
		res.setDifference(bin.getDifference());
		res.setIncomeTax(bin.getIncomeTax());
		res.setEstimation(bin.getEstimation());
		res.setSubmissionDate(bin.getSubmissionDate());
		res.setYear(bin.getYear());
		res.setMonth(bin.getMonth());
		return res;
	}

	public static APIBudgetOut BudgetFromModel(Budget budget) {
		APIBudgetOut res = new APIBudgetOut();
		res.setEid(budget.getIid());
		res.setName(budget.getName());
		res.setDifference(budget.getDifference());
		res.setIncomeTax(budget.getIncomeTax());
		res.setEstimation(budget.getEstimation());
		res.setSubmissionDate(budget.getSubmissionDate());
		res.setYear(budget.getYear());
		res.setMonth(budget.getMonth());
		res.setFacilityId(budget.getFacilityId());
		res.setTypeId(budget.getTypeId());
		return res;
	}
}
