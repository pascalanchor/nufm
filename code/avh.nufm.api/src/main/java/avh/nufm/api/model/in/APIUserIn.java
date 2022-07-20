package avh.nufm.api.model.in;

import java.util.ArrayList;

import avh.nufm.api.model.APIUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIUserIn extends APIUser {
	private String password;
}
