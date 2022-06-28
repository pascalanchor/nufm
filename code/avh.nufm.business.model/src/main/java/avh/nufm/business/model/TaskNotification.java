package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the task_notification database table.
 * 
 */
@Entity
@Table(name="task_notification")
@NamedQuery(name="TaskNotification.findAll", query="SELECT t FROM TaskNotification t")
public class TaskNotification implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String comment;

	private Timestamp date;

	@Column(name="document_id")
	private String documentId;

	private String status;

	//bi-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="receiver_id")
	private NufmUser nufmUser1;

	//bi-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="sender_id")
	private NufmUser nufmUser2;

	//bi-directional many-to-one association to Task
	@ManyToOne
	@JoinColumn(name="task_id")
	private Task task;

	public TaskNotification() {
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

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}