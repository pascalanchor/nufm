package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name="user_role")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Column(name="creation_date")
	private Timestamp creationDate;

	//uni-directional many-to-one association to NufmRole
	@ManyToOne
	@JoinColumn(name="role_id")
	private NufmRole nufmRole;

	//uni-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private NufmUser nufmUser;

	public UserRole() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public NufmRole getNufmRole() {
		return this.nufmRole;
	}

	public void setNufmRole(NufmRole nufmRole) {
		this.nufmRole = nufmRole;
	}

	public NufmUser getNufmUser() {
		return this.nufmUser;
	}

	public void setNufmUser(NufmUser nufmUser) {
		this.nufmUser = nufmUser;
	}

}