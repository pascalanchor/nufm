package avh.nufm.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIUser {

/*id*/private String email;
	private String fullName;
	private String nationalIdentity;
	private String specialization;
	private String phone;
}
