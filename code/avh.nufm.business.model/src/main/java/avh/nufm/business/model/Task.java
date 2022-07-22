package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the task database table.
 * 
 */
@Entity
@NamedQuery(name="Task.findAll", query="SELECT t FROM Task t")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String comment;

	@Column(name="created_at")
	private String createdAt;

	@Temporal(TemporalType.DATE)
	@Column(name="date_from")
	private Date dateFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="date_to")
	private Date dateTo;

	@Column(name="document_id")
	private String documentId;

	private String name;

	private String status;

	//bi-directional many-to-one association to Facility
	@ManyToOne
	@JoinColumn(name="facility_id")
	private Facility facility;

	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;

	//bi-directional many-to-one association to TaskType
	@ManyToOne
	@JoinColumn(name="type_id")
	private TaskType taskType;

	//bi-directional many-to-one association to WorkerSchedule
	@OneToMany(mappedBy="task")
	private List<WorkerSchedule> workerSchedules;

	//bi-directional many-to-one association to WorkerTask
	@OneToMany(mappedBy="task",cascade = {CascadeType.REMOVE})
	private List<WorkerTask> workerTasks;

	public Task() {
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

	public String getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDateFrom() {
		return this.dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return this.dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public TaskType getTaskType() {
		return this.taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public List<WorkerSchedule> getWorkerSchedules() {
		return this.workerSchedules;
	}

	public void setWorkerSchedules(List<WorkerSchedule> workerSchedules) {
		this.workerSchedules = workerSchedules;
	}

	public WorkerSchedule addWorkerSchedule(WorkerSchedule workerSchedule) {
		getWorkerSchedules().add(workerSchedule);
		workerSchedule.setTask(this);

		return workerSchedule;
	}

	public WorkerSchedule removeWorkerSchedule(WorkerSchedule workerSchedule) {
		getWorkerSchedules().remove(workerSchedule);
		workerSchedule.setTask(null);

		return workerSchedule;
	}

	public List<WorkerTask> getWorkerTasks() {
		return this.workerTasks;
	}

	public void setWorkerTasks(List<WorkerTask> workerTasks) {
		this.workerTasks = workerTasks;
	}

	public WorkerTask addWorkerTask(WorkerTask workerTask) {
		getWorkerTasks().add(workerTask);
		workerTask.setTask(this);

		return workerTask;
	}

	public WorkerTask removeWorkerTask(WorkerTask workerTask) {
		getWorkerTasks().remove(workerTask);
		workerTask.setTask(null);

		return workerTask;
	}

}