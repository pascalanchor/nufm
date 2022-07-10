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

	//bi-directional many-to-one association to Equipment
	@ManyToOne
	@JoinColumn(name="equipment_id")
	private Equipment equipment;

	//bi-directional many-to-one association to Facility
	@ManyToOne
	@JoinColumn(name="facility_id")
	private Facility facility;

	public FacilityEquipment() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Equipment getEquipment() {
		return this.equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public Facility getFacility() {
		return this.facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

}