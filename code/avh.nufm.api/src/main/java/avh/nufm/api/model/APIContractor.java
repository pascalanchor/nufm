package avh.nufm.api.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIContractor {
	/*id*/private String email;
	private String fullName;
	private String nationalIdentity;
	private String phone;
	private List<String> specializations;
}
