package avh.nufm.api.model.out;

import java.sql.Timestamp;
import java.util.List;

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
private APIOccupantOut occupant;
private List<String> docs;
}
