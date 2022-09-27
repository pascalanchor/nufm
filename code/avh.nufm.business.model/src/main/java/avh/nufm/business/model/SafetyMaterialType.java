package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the safety_material_type database table.
 * 
 */
@Entity
@Table(name="safety_material_type")
@NamedQuery(name="SafetyMaterialType.findAll", query="SELECT s FROM SafetyMaterialType s")
public class SafetyMaterialType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String name;

	public SafetyMaterialType() {
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