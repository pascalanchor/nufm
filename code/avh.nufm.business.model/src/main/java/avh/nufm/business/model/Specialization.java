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

	//bi-directional many-to-one association to UserSpecialization
	@OneToMany(mappedBy="specialization")
	private List<UserSpecialization> userSpecializations;

//	//bi-directional many-to-one association to UserSpecialization
//	@OneToMany(mappedBy="specialization2")
//	private List<UserSpecialization> userSpecializations2;

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

	public List<UserSpecialization> getUserSpecializations() {
		return this.userSpecializations;
	}

	public void setUserSpecializations(List<UserSpecialization> userSpecializations) {
		this.userSpecializations = userSpecializations;
	}

	public UserSpecialization addUserSpecializations1(UserSpecialization userSpecializations) {
		getUserSpecializations().add(userSpecializations);
		userSpecializations.setSpecialization(this);

		return userSpecializations;
	}

	public UserSpecialization removeUserSpecializations(UserSpecialization userSpecializations) {
		getUserSpecializations().remove(userSpecializations);
		userSpecializations.setSpecialization(null);

		return userSpecializations;
	}

//	public List<UserSpecialization> getUserSpecializations2() {
//		return this.userSpecializations2;
//	}
//
//	public void setUserSpecializations2(List<UserSpecialization> userSpecializations2) {
//		this.userSpecializations2 = userSpecializations2;
//	}
//
//	public UserSpecialization addUserSpecializations2(UserSpecialization userSpecializations2) {
//		getUserSpecializations2().add(userSpecializations2);
//		userSpecializations2.setSpecialization2(this);
//
//		return userSpecializations2;
//	}
//
//	public UserSpecialization removeUserSpecializations2(UserSpecialization userSpecializations2) {
//		getUserSpecializations2().remove(userSpecializations2);
//		userSpecializations2.setSpecialization2(null);
//
//		return userSpecializations2;
//	}

}