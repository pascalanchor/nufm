package avh.nufm.api.model.out;

import java.sql.Timestamp;
import java.util.ArrayList;

import avh.nufm.api.model.APIUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIUserOut extends APIUser {
	private String token;
	private String profileImage;
	private Timestamp creationDate;
	private ArrayList<String> roles = new ArrayList<String>();
	
	public Iterable<String> getRoles() {
		return roles;
	}

	public void addRole(String r) {
		this.roles.add(r);
	}
	
	public void removeRole(String r) {
		this.roles.remove(r);
	}
}
