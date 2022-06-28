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

	//bi-directional many-to-one association to NufmUser
	@OneToMany(mappedBy="specialization")
	private List<NufmUser> nufmUsers;

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

	public List<NufmUser> getNufmUsers() {
		return this.nufmUsers;
	}

	public void setNufmUsers(List<NufmUser> nufmUsers) {
		this.nufmUsers = nufmUsers;
	}

	public NufmUser addNufmUser(NufmUser nufmUser) {
		getNufmUsers().add(nufmUser);
		nufmUser.setSpecialization(this);

		return nufmUser;
	}

	public NufmUser removeNufmUser(NufmUser nufmUser) {
		getNufmUsers().remove(nufmUser);
		nufmUser.setSpecialization(null);

		return nufmUser;
	}

}