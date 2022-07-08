package avh.nufm.api.model.out;

import java.sql.Timestamp;

import avh.nufm.api.model.APIFacility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIFacilityOut extends APIFacility{
private String eid;
private Timestamp createdAt;
private String occupant_id;


}
