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

	@Column(name="created_at")
	private Timestamp createdAt;

	private Boolean enabled;

	@Column(name="full_name")
	private String fullName;

	private String password;

	private String phone;

	@Column(name="profile_image")
	private String profileImage;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	//bi-directional many-to-one association to Attendance
	@OneToMany(mappedBy="nufmUser")
	private List<Attendance> attendances;

	//bi-directional many-to-one association to ConfirmationToken
	@OneToMany(mappedBy="nufmUser")
	private List<ConfirmationToken> confirmationTokens;

	//bi-directional many-to-one association to Facility
	@OneToMany(mappedBy="nufmUser")
	private List<Facility> facilities;

	//bi-directional many-to-one association to ProjectWorker
	@OneToMany(mappedBy="nufmUser")
	private List<ProjectWorker> projectWorkers;

	//bi-directional many-to-one association to SafetyWorker
	@OneToMany(mappedBy="nufmUser")
	private List<SafetyWorker> safetyWorkers;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="nufmUser")
	private List<UserRole> userRoles;

	//bi-directional many-to-one association to UserSpecialization
	@OneToMany(mappedBy="nufmUser")
	private List<UserSpecialization> userSpecializations;

	//bi-directional many-to-one association to WorkerSchedule
	@OneToMany(mappedBy="nufmUser")
	private List<WorkerSchedule> workerSchedules;

	//bi-directional many-to-one association to WorkerTask
	@OneToMany(mappedBy="nufmUser")
	private List<WorkerTask> workerTasks;

	//bi-directional many-to-one association to Notification
	@OneToMany(mappedBy="nufmUser1")
	private List<Notification> notifications1;

	//bi-directional many-to-one association to Notification
	@OneToMany(mappedBy="nufmUser2")
	private List<Notification> notifications2;

	public NufmUser() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getProfileImage() {
		return this.profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
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

	public List<ConfirmationToken> getConfirmationTokens() {
		return this.confirmationTokens;
	}

	public void setConfirmationTokens(List<ConfirmationToken> confirmationTokens) {
		this.confirmationTokens = confirmationTokens;
	}

	public ConfirmationToken addConfirmationToken(ConfirmationToken confirmationToken) {
		getConfirmationTokens().add(confirmationToken);
		confirmationToken.setNufmUser(this);

		return confirmationToken;
	}

	public ConfirmationToken removeConfirmationToken(ConfirmationToken confirmationToken) {
		getConfirmationTokens().remove(confirmationToken);
		confirmationToken.setNufmUser(null);

		return confirmationToken;
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

	public List<UserSpecialization> getUserSpecializations() {
		return this.userSpecializations;
	}

	public void setUserSpecializations(List<UserSpecialization> userSpecializations) {
		this.userSpecializations = userSpecializations;
	}

	public UserSpecialization addUserSpecialization(UserSpecialization userSpecialization) {
		getUserSpecializations().add(userSpecialization);
		userSpecialization.setNufmUser(this);

		return userSpecialization;
	}

	public UserSpecialization removeUserSpecialization(UserSpecialization userSpecialization) {
		getUserSpecializations().remove(userSpecialization);
		userSpecialization.setNufmUser(null);

		return userSpecialization;
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

	public List<Notification> getNotifications1() {
		return this.notifications1;
	}

	public void setNotifications1(List<Notification> notifications1) {
		this.notifications1 = notifications1;
	}

	public Notification addNotifications1(Notification notifications1) {
		getNotifications1().add(notifications1);
		notifications1.setNufmUser1(this);

		return notifications1;
	}

	public Notification removeNotifications1(Notification notifications1) {
		getNotifications1().remove(notifications1);
		notifications1.setNufmUser1(null);

		return notifications1;
	}

	public List<Notification> getNotifications2() {
		return this.notifications2;
	}

	public void setNotifications2(List<Notification> notifications2) {
		this.notifications2 = notifications2;
	}

	public Notification addNotifications2(Notification notifications2) {
		getNotifications2().add(notifications2);
		notifications2.setNufmUser2(this);

		return notifications2;
	}

	public Notification removeNotifications2(Notification notifications2) {
		getNotifications2().remove(notifications2);
		notifications2.setNufmUser2(null);

		return notifications2;
	}

}