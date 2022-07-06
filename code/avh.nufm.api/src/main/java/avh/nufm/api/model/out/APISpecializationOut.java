package avh.nufm.api.model.out;

import avh.nufm.api.model.APISpecialization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APISpecializationOut extends APISpecialization{
	private String eid;
}
