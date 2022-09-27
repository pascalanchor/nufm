package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the equipment_type database table.
 * 
 */
@Entity
@Table(name="equipment_type")
@NamedQuery(name="EquipmentType.findAll", query="SELECT e FROM EquipmentType e")
public class EquipmentType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String name;

	public EquipmentType() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}