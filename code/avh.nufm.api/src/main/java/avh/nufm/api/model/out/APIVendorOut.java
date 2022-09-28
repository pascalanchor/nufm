package avh.nufm.api.model.out;

import avh.nufm.api.model.APIVendor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIVendorOut extends APIVendor {
	private String eid;
}
