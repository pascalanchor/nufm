package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the safety_material database table.
 * 
 */
@Entity
@Table(name="safety_material")
@NamedQuery(name="SafetyMaterial.findAll", query="SELECT s FROM SafetyMaterial s")
public class SafetyMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String name;

	private String status;

	//uni-directional many-to-one association to SafetyMaterialType
	@ManyToOne
	@JoinColumn(name="type")
	private SafetyMaterialType safetyMaterialType;

	public SafetyMaterial() {
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SafetyMaterialType getSafetyMaterialType() {
		return this.safetyMaterialType;
	}

	public void setSafetyMaterialType(SafetyMaterialType safetyMaterialType) {
		this.safetyMaterialType = safetyMaterialType;
	}

}