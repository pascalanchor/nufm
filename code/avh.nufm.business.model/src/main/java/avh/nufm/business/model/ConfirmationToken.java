package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the confirmation_token database table.
 * 
 */
@Entity
@Table(name="confirmation_token")
@NamedQuery(name="ConfirmationToken.findAll", query="SELECT c FROM ConfirmationToken c")
public class ConfirmationToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iid;

	@Column(name="confirmed_at")
	private Timestamp confirmedAt;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="expired_at")
	private Timestamp expiredAt;

	private String token;

	//uni-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private NufmUser nufmUser;

	public ConfirmationToken() {
	}

	public String getIid() {
		return this.iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public Timestamp getConfirmedAt() {
		return this.confirmedAt;
	}

	public void setConfirmedAt(Timestamp confirmedAt) {
		this.confirmedAt = confirmedAt;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getExpiredAt() {
		return this.expiredAt;
	}

	public void setExpiredAt(Timestamp expiredAt) {
		this.expiredAt = expiredAt;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public NufmUser getNufmUser() {
		return this.nufmUser;
	}

	public void setNufmUser(NufmUser nufmUser) {
		this.nufmUser = nufmUser;
	}

}