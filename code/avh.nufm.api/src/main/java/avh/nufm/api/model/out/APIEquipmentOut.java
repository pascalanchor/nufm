package avh.nufm.api.model.out;

import avh.nufm.api.model.APIEquipment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIEquipmentOut extends APIEquipment {
	private String eid;
}
