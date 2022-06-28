package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the task_request database table.
 * 
 */
@Entity
@Table(name="task_request")
@NamedQuery(name="TaskRequest.findAll", query="SELECT t FROM TaskRequest t")
public class TaskRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private Timestamp date;

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

	public TaskRequest() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
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