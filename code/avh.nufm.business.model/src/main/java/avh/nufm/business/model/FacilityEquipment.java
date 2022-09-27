package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the facility_equipment database table.
 * 
 */
@Entity
@Table(name="facility_equipment")
@NamedQuery(name="FacilityEquipment.findAll", query="SELECT f FROM FacilityEquipment f")
public class FacilityEquipment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	@Column(name="equipment_id")
	private String equipmentId;

	@Column(name="facility_id")
	private String facilityId;

	public FacilityEquipment() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getEquipmentId() {
		return this.equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

}