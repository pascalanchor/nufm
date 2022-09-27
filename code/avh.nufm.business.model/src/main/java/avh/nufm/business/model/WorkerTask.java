package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the worker_task database table.
 * 
 */
@Entity
@Table(name="worker_task")
@NamedQuery(name="WorkerTask.findAll", query="SELECT w FROM WorkerTask w")
public class WorkerTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Column(name="assigned_date")
	private Timestamp assignedDate;

	//uni-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="worker_id")
	private NufmUser nufmUser;

	//uni-directional many-to-one association to Task
	@ManyToOne
	@JoinColumn(name="task_id")
	private Task task;

	public WorkerTask() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Timestamp getAssignedDate() {
		return this.assignedDate;
	}

	public void setAssignedDate(Timestamp assignedDate) {
		this.assignedDate = assignedDate;
	}

	public NufmUser getNufmUser() {
		return this.nufmUser;
	}

	public void setNufmUser(NufmUser nufmUser) {
		this.nufmUser = nufmUser;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}