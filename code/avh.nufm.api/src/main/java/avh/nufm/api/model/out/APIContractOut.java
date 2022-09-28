package avh.nufm.api.model.out;

import avh.nufm.api.model.APIContract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIContractOut extends APIContract {
	private String eid;
}
