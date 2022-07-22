package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the facility database table.
 * 
 */
@Entity
@NamedQuery(name="Facility.findAll", query="SELECT f FROM Facility f")
public class Facility implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Column(name="creation_date")
	private Timestamp creationDate;

	private String location;

	private String name;

	//bi-directional many-to-one association to Facility
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Facility facility;

	//bi-directional many-to-one association to Facility
	@OneToMany(mappedBy="facility")
	private List<Facility> facilities;

	//bi-directional many-to-one association to FacilityType
	@ManyToOne
	@JoinColumn(name="type_id")
	private FacilityType facilityType;

	//bi-directional many-to-one association to NufmUser
	@ManyToOne(cascade = {CascadeType.REMOVE})
	@JoinColumn(name="occupant_id")
	private NufmUser nufmUser;

	//bi-directional many-to-one association to FacilityDocument
	@OneToMany(mappedBy="facility",cascade = {CascadeType.REMOVE})
	private List<FacilityDocument> facilityDocuments;

	//bi-directional many-to-one association to FacilityEquipment
	@OneToMany(mappedBy="facility")
	private List<FacilityEquipment> facilityEquipments;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="facility",cascade = {CascadeType.REMOVE})
	private List<Project> projects;

	//bi-directional many-to-one association to Task
	@OneToMany(mappedBy="facility",cascade = {CascadeType.REMOVE})
	private List<Task> tasks;

	public Facility() {
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

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Facility getFacility() {
		return this.facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public List<Facility> getFacilities() {
		return this.facilities;
	}

	public void setFacilities(List<Facility> facilities) {
		this.facilities = facilities;
	}

	public Facility addFacility(Facility facility) {
		getFacilities().add(facility);
		facility.setFacility(this);

		return facility;
	}

	public Facility removeFacility(Facility facility) {
		getFacilities().remove(facility);
		facility.setFacility(null);

		return facility;
	}

	public FacilityType getFacilityType() {
		return this.facilityType;
	}

	public void setFacilityType(FacilityType facilityType) {
		this.facilityType = facilityType;
	}

	public NufmUser getNufmUser() {
		return this.nufmUser;
	}

	public void setNufmUser(NufmUser nufmUser) {
		this.nufmUser = nufmUser;
	}

	public List<FacilityDocument> getFacilityDocuments() {
		return this.facilityDocuments;
	}

	public void setFacilityDocuments(List<FacilityDocument> facilityDocuments) {
		this.facilityDocuments = facilityDocuments;
	}

	public FacilityDocument addFacilityDocument(FacilityDocument facilityDocument) {
		getFacilityDocuments().add(facilityDocument);
		facilityDocument.setFacility(this);

		return facilityDocument;
	}

	public FacilityDocument removeFacilityDocument(FacilityDocument facilityDocument) {
		getFacilityDocuments().remove(facilityDocument);
		facilityDocument.setFacility(null);

		return facilityDocument;
	}

	public List<FacilityEquipment> getFacilityEquipments() {
		return this.facilityEquipments;
	}

	public void setFacilityEquipments(List<FacilityEquipment> facilityEquipments) {
		this.facilityEquipments = facilityEquipments;
	}

	public FacilityEquipment addFacilityEquipment(FacilityEquipment facilityEquipment) {
		getFacilityEquipments().add(facilityEquipment);
		facilityEquipment.setFacility(this);

		return facilityEquipment;
	}

	public FacilityEquipment removeFacilityEquipment(FacilityEquipment facilityEquipment) {
		getFacilityEquipments().remove(facilityEquipment);
		facilityEquipment.setFacility(null);

		return facilityEquipment;
	}

	public List<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Project addProject(Project project) {
		getProjects().add(project);
		project.setFacility(this);

		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		project.setFacility(null);

		return project;
	}

	public List<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task addTask(Task task) {
		getTasks().add(task);
		task.setFacility(this);

		return task;
	}

	public Task removeTask(Task task) {
		getTasks().remove(task);
		task.setFacility(null);

		return task;
	}

}