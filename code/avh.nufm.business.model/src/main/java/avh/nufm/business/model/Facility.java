package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


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

	private String address;

	@Column(name="business_id")
	private String businessId;

	@Column(name="const_year")
	private Integer constYear;

	@Column(name="creation_date")
	private Timestamp creationDate;

	@Temporal(TemporalType.DATE)
	@Column(name="date_opened")
	private Date dateOpened;

	private String description;

	@Column(name="floors_count")
	private Integer floorsCount;

	private String location;

	private String name;

	@Column(name="organizational_unit")
	private String organizationalUnit;

	@Column(name="primary_email")
	private String primaryEmail;

	@Temporal(TemporalType.DATE)
	@Column(name="schedule_from")
	private Date scheduleFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="schedule_to")
	private Date scheduleTo;

	private String street;

	@Column(name="zip_code")
	private String zipCode;

	//uni-directional many-to-one association to Facility
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Facility facility;

	//uni-directional many-to-one association to FacilityType
	@ManyToOne
	@JoinColumn(name="type_id")
	private FacilityType facilityType;

	//uni-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="occupant_id")
	private NufmUser nufmUser;

	public Facility() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Integer getConstYear() {
		return this.constYear;
	}

	public void setConstYear(Integer constYear) {
		this.constYear = constYear;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Date getDateOpened() {
		return this.dateOpened;
	}

	public void setDateOpened(Date dateOpened) {
		this.dateOpened = dateOpened;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFloorsCount() {
		return this.floorsCount;
	}

	public void setFloorsCount(Integer floorsCount) {
		this.floorsCount = floorsCount;
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

	public String getOrganizationalUnit() {
		return this.organizationalUnit;
	}

	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}

	public String getPrimaryEmail() {
		return this.primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public Date getScheduleFrom() {
		return this.scheduleFrom;
	}

	public void setScheduleFrom(Date scheduleFrom) {
		this.scheduleFrom = scheduleFrom;
	}

	public Date getScheduleTo() {
		return this.scheduleTo;
	}

	public void setScheduleTo(Date scheduleTo) {
		this.scheduleTo = scheduleTo;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Facility getFacility() {
		return this.facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
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

}