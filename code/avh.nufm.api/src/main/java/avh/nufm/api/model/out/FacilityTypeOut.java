package avh.nufm.api.model.out;

import avh.nufm.api.model.FacilityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FacilityTypeOut extends FacilityType{
	private String eid;
	
}
