package avh.nufm.api.model.in;




import avh.nufm.api.model.APIFacility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIFacilityIn extends APIFacility{
	private String occupantId;
}
