package avh.nufm.api.model.in;




import java.util.List;

import avh.nufm.api.model.APIFacility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIFacilityIn extends APIFacility{
	private APIOccupantIn occupant;
	private List<String> equipments;
}
