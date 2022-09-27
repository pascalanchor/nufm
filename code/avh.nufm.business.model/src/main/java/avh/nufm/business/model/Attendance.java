package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the attendance database table.
 * 
 */
@Entity
@NamedQuery(name="Attendance.findAll", query="SELECT a FROM Attendance a")
public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Temporal(TemporalType.DATE)
	@Column(name="clocked_in")
	private Date clockedIn;

	@Temporal(TemporalType.DATE)
	@Column(name="clocked_out")
	private Date clockedOut;

	private String status;

	//uni-directional many-to-one association to Facility
	@ManyToOne
	@JoinColumn(name="facility_id")
	private Facility facility;

	//uni-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="worker_id")
	private NufmUser nufmUser;

	public Attendance() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Date getClockedIn() {
		return this.clockedIn;
	}

	public void setClockedIn(Date clockedIn) {
		this.clockedIn = clockedIn;
	}

	public Date getClockedOut() {
		return this.clockedOut;
	}

	public void setClockedOut(Date clockedOut) {
		this.clockedOut = clockedOut;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Facility getFacility() {
		return this.facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public NufmUser getNufmUser() {
		return this.nufmUser;
	}

	public void setNufmUser(NufmUser nufmUser) {
		this.nufmUser = nufmUser;
	}

}