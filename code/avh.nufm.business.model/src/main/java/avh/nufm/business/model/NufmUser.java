package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the nufm_user database table.
 * 
 */
@Entity
@Table(name="nufm_user")
@NamedQuery(name="NufmUser.findAll", query="SELECT n FROM NufmUser n")
public class NufmUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Column(name="full_name")
	private String fullName;

	@Column(name="national_id")
	private String nationalId;

	private String password;

	private String phone;

	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="nufmUser")
	private List<Attendance> attendances;

	//bi-directional many-to-one association to Facility
	@OneToMany(mappedBy="nufmUser")
	private List<Facility> facilities;

	//bi-directional many-to-one association to Specialization
	@ManyToOne
	@JoinColumn(name="spec_id")
	private Specialization specialization;

	//bi-directional many-to-one association to ProjectWorker
	@OneToMany(mappedBy="nufmUser")
	private List<ProjectWorker> projectWorkers;

	//bi-directional many-to-one association to SafetyWorker
	@OneToMany(mappedBy="nufmUser")
	private List<SafetyWorker> safetyWorkers;

	//bi-directional many-to-one association to TaskNotification
	@OneToMany(mappedBy="nufmUser1")
	private List<TaskNotification> taskNotifications1;

	//bi-directional many-to-one association to TaskNotification
	@OneToMany(mappedBy="nufmUser2")
	private List<TaskNotification> taskNotifications2;

	//bi-directional many-to-one association to TaskRequest
	@OneToMany(mappedBy="nufmUser1")
	private List<TaskRequest> taskRequests1;

	//bi-directional many-to-one association to TaskRequest
	@OneToMany(mappedBy="nufmUser2")
	private List<TaskRequest> taskRequests2;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="nufmUser")
	private List<UserRole> userRoles;

	//bi-directional many-to-one association to WorkerSchedule
	@OneToMany(mappedBy="nufmUser")
	private List<WorkerSchedule> workerSchedules;

	//bi-directional many-to-one association to WorkerTask
	@OneToMany(mappedBy="nufmUser")
	private List<WorkerTask> workerTasks;

	public NufmUser() {
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

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNationalId() {
		return this.nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public Attendance addAttendance(Attendance attendance) {
		getAttendances().add(attendance);
		attendance.setNufmUser(this);

		return attendance;
	}

	public Attendance removeAttendance(Attendance attendance) {
		getAttendances().remove(attendance);
		attendance.setNufmUser(null);

		return attendance;
	}

	public List<Facility> getFacilities() {
		return this.facilities;
	}

	public void setFacilities(List<Facility> facilities) {
		this.facilities = facilities;
	}

	public Facility addFacility(Facility facility) {
		getFacilities().add(facility);
		facility.setNufmUser(this);

		return facility;
	}

	public Facility removeFacility(Facility facility) {
		getFacilities().remove(facility);
		facility.setNufmUser(null);

		return facility;
	}

	public Specialization getSpecialization() {
		return this.specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	public List<ProjectWorker> getProjectWorkers() {
		return this.projectWorkers;
	}

	public void setProjectWorkers(List<ProjectWorker> projectWorkers) {
		this.projectWorkers = projectWorkers;
	}

	public ProjectWorker addProjectWorker(ProjectWorker projectWorker) {
		getProjectWorkers().add(projectWorker);
		projectWorker.setNufmUser(this);

		return projectWorker;
	}

	public ProjectWorker removeProjectWorker(ProjectWorker projectWorker) {
		getProjectWorkers().remove(projectWorker);
		projectWorker.setNufmUser(null);

		return projectWorker;
	}

	public List<SafetyWorker> getSafetyWorkers() {
		return this.safetyWorkers;
	}

	public void setSafetyWorkers(List<SafetyWorker> safetyWorkers) {
		this.safetyWorkers = safetyWorkers;
	}

	public SafetyWorker addSafetyWorker(SafetyWorker safetyWorker) {
		getSafetyWorkers().add(safetyWorker);
		safetyWorker.setNufmUser(this);

		return safetyWorker;
	}

	public SafetyWorker removeSafetyWorker(SafetyWorker safetyWorker) {
		getSafetyWorkers().remove(safetyWorker);
		safetyWorker.setNufmUser(null);

		return safetyWorker;
	}

	public List<TaskNotification> getTaskNotifications1() {
		return this.taskNotifications1;
	}

	public void setTaskNotifications1(List<TaskNotification> taskNotifications1) {
		this.taskNotifications1 = taskNotifications1;
	}

	public TaskNotification addTaskNotifications1(TaskNotification taskNotifications1) {
		getTaskNotifications1().add(taskNotifications1);
		taskNotifications1.setNufmUser1(this);

		return taskNotifications1;
	}

	public TaskNotification removeTaskNotifications1(TaskNotification taskNotifications1) {
		getTaskNotifications1().remove(taskNotifications1);
		taskNotifications1.setNufmUser1(null);

		return taskNotifications1;
	}

	public List<TaskNotification> getTaskNotifications2() {
		return this.taskNotifications2;
	}

	public void setTaskNotifications2(List<TaskNotification> taskNotifications2) {
		this.taskNotifications2 = taskNotifications2;
	}

	public TaskNotification addTaskNotifications2(TaskNotification taskNotifications2) {
		getTaskNotifications2().add(taskNotifications2);
		taskNotifications2.setNufmUser2(this);

		return taskNotifications2;
	}

	public TaskNotification removeTaskNotifications2(TaskNotification taskNotifications2) {
		getTaskNotifications2().remove(taskNotifications2);
		taskNotifications2.setNufmUser2(null);

		return taskNotifications2;
	}

	public List<TaskRequest> getTaskRequests1() {
		return this.taskRequests1;
	}

	public void setTaskRequests1(List<TaskRequest> taskRequests1) {
		this.taskRequests1 = taskRequests1;
	}

	public TaskRequest addTaskRequests1(TaskRequest taskRequests1) {
		getTaskRequests1().add(taskRequests1);
		taskRequests1.setNufmUser1(this);

		return taskRequests1;
	}

	public TaskRequest removeTaskRequests1(TaskRequest taskRequests1) {
		getTaskRequests1().remove(taskRequests1);
		taskRequests1.setNufmUser1(null);

		return taskRequests1;
	}

	public List<TaskRequest> getTaskRequests2() {
		return this.taskRequests2;
	}

	public void setTaskRequests2(List<TaskRequest> taskRequests2) {
		this.taskRequests2 = taskRequests2;
	}

	public TaskRequest addTaskRequests2(TaskRequest taskRequests2) {
		getTaskRequests2().add(taskRequests2);
		taskRequests2.setNufmUser2(this);

		return taskRequests2;
	}

	public TaskRequest removeTaskRequests2(TaskRequest taskRequests2) {
		getTaskRequests2().remove(taskRequests2);
		taskRequests2.setNufmUser2(null);

		return taskRequests2;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setNufmUser(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setNufmUser(null);

		return userRole;
	}

	public List<WorkerSchedule> getWorkerSchedules() {
		return this.workerSchedules;
	}

	public void setWorkerSchedules(List<WorkerSchedule> workerSchedules) {
		this.workerSchedules = workerSchedules;
	}

	public WorkerSchedule addWorkerSchedule(WorkerSchedule workerSchedule) {
		getWorkerSchedules().add(workerSchedule);
		workerSchedule.setNufmUser(this);

		return workerSchedule;
	}

	public WorkerSchedule removeWorkerSchedule(WorkerSchedule workerSchedule) {
		getWorkerSchedules().remove(workerSchedule);
		workerSchedule.setNufmUser(null);

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
		workerTask.setNufmUser(this);

		return workerTask;
	}

	public WorkerTask removeWorkerTask(WorkerTask workerTask) {
		getWorkerTasks().remove(workerTask);
		workerTask.setNufmUser(null);

		return workerTask;
	}

}