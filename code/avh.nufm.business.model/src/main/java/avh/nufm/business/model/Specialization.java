package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the specialization database table.
 * 
 */
@Entity
@NamedQuery(name="Specialization.findAll", query="SELECT s FROM Specialization s")
public class Specialization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String name;

	private String type;

	//bi-directional many-to-one association to UserSpecialization
	@OneToMany(mappedBy="specialization1")
	private List<UserSpecialization> userSpecializations1;

	//bi-directional many-to-one association to UserSpecialization
	@OneToMany(mappedBy="specialization2")
	private List<UserSpecialization> userSpecializations2;

	public Specialization() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<UserSpecialization> getUserSpecializations1() {
		return this.userSpecializations1;
	}

	public void setUserSpecializations1(List<UserSpecialization> userSpecializations1) {
		this.userSpecializations1 = userSpecializations1;
	}

	public UserSpecialization addUserSpecializations1(UserSpecialization userSpecializations1) {
		getUserSpecializations1().add(userSpecializations1);
		userSpecializations1.setSpecialization1(this);

		return userSpecializations1;
	}

	public UserSpecialization removeUserSpecializations1(UserSpecialization userSpecializations1) {
		getUserSpecializations1().remove(userSpecializations1);
		userSpecializations1.setSpecialization1(null);

		return userSpecializations1;
	}

	public List<UserSpecialization> getUserSpecializations2() {
		return this.userSpecializations2;
	}

	public void setUserSpecializations2(List<UserSpecialization> userSpecializations2) {
		this.userSpecializations2 = userSpecializations2;
	}

	public UserSpecialization addUserSpecializations2(UserSpecialization userSpecializations2) {
		getUserSpecializations2().add(userSpecializations2);
		userSpecializations2.setSpecialization2(this);

		return userSpecializations2;
	}

	public UserSpecialization removeUserSpecializations2(UserSpecialization userSpecializations2) {
		getUserSpecializations2().remove(userSpecializations2);
		userSpecializations2.setSpecialization2(null);

		return userSpecializations2;
	}

}