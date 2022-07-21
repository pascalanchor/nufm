package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the notification database table.
 * 
 */
@Entity
@NamedQuery(name="Notification.findAll", query="SELECT n FROM Notification n")
public class Notification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String comment;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Temporal(TemporalType.DATE)
	@Column(name="delivery_date")
	private Date deliveryDate;

	@Column(name="facility_name")
	private String facilityName;

	@Column(name="sender_name")
	private String senderName;

	@Temporal(TemporalType.DATE)
	@Column(name="task_date")
	private Date taskDate;

	@Column(name="task_name")
	private String taskName;

	@Column(name="task_status")
	private String taskStatus;

	@Column(name="task_type")
	private String taskType;

	//bi-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="receiver_id")
	private NufmUser nufmUser1;

	//bi-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="sender_id")
	private NufmUser nufmUser2;

	public Notification() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getFacilityName() {
		return this.facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Date getTaskDate() {
		return this.taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskStatus() {
		return this.taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public NufmUser getNufmUser1() {
		return this.nufmUser1;
	}

	public void setNufmUser1(NufmUser nufmUser1) {
		this.nufmUser1 = nufmUser1;
	}

	public NufmUser getNufmUser2() {
		return this.nufmUser2;
	}

	public void setNufmUser2(NufmUser nufmUser2) {
		this.nufmUser2 = nufmUser2;
	}

}