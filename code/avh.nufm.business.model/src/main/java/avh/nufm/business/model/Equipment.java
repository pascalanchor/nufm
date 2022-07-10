package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the equipment database table.
 * 
 */
@Entity
@NamedQuery(name="Equipment.findAll", query="SELECT e FROM Equipment e")
public class Equipment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Column(name="created_at")
	private Timestamp createdAt;

	private String location;

	@Column(name="monitoring_rules")
	private String monitoringRules;

	private String name;

	private String specification;

	@Column(name="updated_at")
	private Timestamp updatedAt;

	//bi-directional many-to-one association to FacilityEquipment
	@OneToMany(mappedBy="equipment")
	private List<FacilityEquipment> facilityEquipments;

	public Equipment() {
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

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMonitoringRules() {
		return this.monitoringRules;
	}

	public void setMonitoringRules(String monitoringRules) {
		this.monitoringRules = monitoringRules;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecification() {
		return this.specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<FacilityEquipment> getFacilityEquipments() {
		return this.facilityEquipments;
	}

	public void setFacilityEquipments(List<FacilityEquipment> facilityEquipments) {
		this.facilityEquipments = facilityEquipments;
	}

	public FacilityEquipment addFacilityEquipment(FacilityEquipment facilityEquipment) {
		getFacilityEquipments().add(facilityEquipment);
		facilityEquipment.setEquipment(this);

		return facilityEquipment;
	}

	public FacilityEquipment removeFacilityEquipment(FacilityEquipment facilityEquipment) {
		getFacilityEquipments().remove(facilityEquipment);
		facilityEquipment.setEquipment(null);

		return facilityEquipment;
	}

}