package avh.nufm.api.model.in;




import avh.nufm.api.model.Facility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FacilityIn extends Facility{

private String parent_id;
private String occupant_id;
}
