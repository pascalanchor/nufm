package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_specialization database table.
 * 
 */
@Entity
@Table(name="user_specialization")
@NamedQuery(name="UserSpecialization.findAll", query="SELECT u FROM UserSpecialization u")
public class UserSpecialization implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	//uni-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private NufmUser nufmUser;

	//uni-directional many-to-one association to Specialization
	@ManyToOne
	@JoinColumn(name="specialization_id")
	private Specialization specialization;

	public UserSpecialization() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public NufmUser getNufmUser() {
		return this.nufmUser;
	}

	public void setNufmUser(NufmUser nufmUser) {
		this.nufmUser = nufmUser;
	}

	public Specialization getSpecialization() {
		return this.specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

}