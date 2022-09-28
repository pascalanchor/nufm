package avh.nufm.api.model.out;

import avh.nufm.api.model.APIBudget;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIBudgetOut extends APIBudget {
	private String eid;
}
