package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

	//bi-directional many-to-one association to SafetyMaterial
	@OneToMany(mappedBy="safetyMaterialType")
	private List<SafetyMaterial> safetyMaterials;

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

	public List<SafetyMaterial> getSafetyMaterials() {
		return this.safetyMaterials;
	}

	public void setSafetyMaterials(List<SafetyMaterial> safetyMaterials) {
		this.safetyMaterials = safetyMaterials;
	}

	public SafetyMaterial addSafetyMaterial(SafetyMaterial safetyMaterial) {
		getSafetyMaterials().add(safetyMaterial);
		safetyMaterial.setSafetyMaterialType(this);

		return safetyMaterial;
	}

	public SafetyMaterial removeSafetyMaterial(SafetyMaterial safetyMaterial) {
		getSafetyMaterials().remove(safetyMaterial);
		safetyMaterial.setSafetyMaterialType(null);

		return safetyMaterial;
	}

}