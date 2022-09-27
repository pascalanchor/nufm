package avh.nufm.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import avh.nufm.api.common.PathCte;
import avh.nufm.api.impl.BudgetControllerImpl;
import avh.nufm.api.model.in.APIBudgetIn;
import avh.nufm.api.model.out.APIBudgetOut;
import avh.nufm.api.model.transformer.BudgetTransformer;
import avh.nufm.business.model.Budget;

@RestController
public class BudgetController {
	@Autowired
	BudgetControllerImpl budgetConImpl;

	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping(PathCte.AddBudgetServletPath)
	public ResponseEntity<APIBudgetOut> createBudget(@RequestParam("data") String data) {
		try {
			APIBudgetIn budgetIn = new ObjectMapper().readValue(data, APIBudgetIn.class);
			Budget budgetModel = BudgetTransformer.BudgetToModel(budgetIn);
			Budget budget = budgetConImpl.addBudget(budgetModel,budgetIn.getFacilityId(),budgetIn.getTypeId());
			return ResponseEntity.ok().body(BudgetTransformer.BudgetFromModel(budget));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// DELETE CONTRACTOR
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@DeleteMapping(PathCte.DeleteBudgetServletPath + "/{id}")
	public ResponseEntity<String> deleteBudget(@PathVariable("id") String id) {
		try {
			budgetConImpl.deleteBudget(id);
			return ResponseEntity.ok().body("budget has been deleted successfully");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// GET BUDGETS
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR','WORKER','OCCUPANT')")
	@GetMapping(PathCte.GetAllBudgetsServletPath)
	public ResponseEntity<List<APIBudgetOut>> getBudgets() {
		try {
			List<Budget> budgets = budgetConImpl.getBudgets();
			List<APIBudgetOut> res = new ArrayList<>();
			budgets.stream().forEach(e -> res.add(BudgetTransformer.BudgetFromModel(e)));
			return ResponseEntity.ok().body(res);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// GET BUDGET BY ID
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@GetMapping(PathCte.GetBudgetByIdServletPath + "/{id}")
	public ResponseEntity<APIBudgetOut> getBudgetById(@PathVariable("id") String id) {
		try {
			Budget res = budgetConImpl.getBudgetById(id);
			return ResponseEntity.ok().body(BudgetTransformer.BudgetFromModel(res));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
		}
	}

	// UPDATE BUDGET
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PutMapping(PathCte.UpdateBudgetServletPath)
	public ResponseEntity<String> updateBudget(@RequestParam("data") String data,@RequestParam("budgetId") String id) {
		try {
			APIBudgetIn budgetIn = new ObjectMapper().readValue(data, APIBudgetIn.class);
			Budget budgetModel = BudgetTransformer.BudgetToModel(budgetIn);
			budgetModel = budgetConImpl.updateBudget(id,budgetModel,budgetIn.getFacilityId(),budgetIn.getTypeId());
			return ResponseEntity.ok().body("budget is updated");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
		}
	}
}
