package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the worker_schedule database table.
 * 
 */
@Entity
@Table(name="worker_schedule")
@NamedQuery(name="WorkerSchedule.findAll", query="SELECT w FROM WorkerSchedule w")
public class WorkerSchedule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Temporal(TemporalType.DATE)
	private Date date;

	//uni-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="worker_id")
	private NufmUser nufmUser;

	//uni-directional many-to-one association to Task
	@ManyToOne
	@JoinColumn(name="task_id")
	private Task task;

	public WorkerSchedule() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
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