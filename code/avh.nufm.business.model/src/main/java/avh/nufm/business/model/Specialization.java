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
	@OneToMany(mappedBy="specialization")
	private List<UserSpecialization> userSpecializations;

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

	public List<UserSpecialization> getUserSpecializations() {
		return this.userSpecializations;
	}

	public void setUserSpecializations(List<UserSpecialization> userSpecializations) {
		this.userSpecializations = userSpecializations;
	}

	public UserSpecialization addUserSpecialization(UserSpecialization userSpecialization) {
		getUserSpecializations().add(userSpecialization);
		userSpecialization.setSpecialization(this);

		return userSpecialization;
	}

	public UserSpecialization removeUserSpecialization(UserSpecialization userSpecialization) {
		getUserSpecializations().remove(userSpecialization);
		userSpecialization.setSpecialization(null);

		return userSpecialization;
	}

}