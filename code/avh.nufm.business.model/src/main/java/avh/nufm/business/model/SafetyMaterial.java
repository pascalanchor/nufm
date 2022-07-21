package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

	private String documentid;

	private String name;

	private String status;

	//bi-directional many-to-one association to SafetyWorker
	@OneToMany(mappedBy="safetyMaterial")
	private List<SafetyWorker> safetyWorkers;

	//bi-directional many-to-one association to SafetyMaterialType
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

	public String getDocumentid() {
		return this.documentid;
	}

	public void setDocumentid(String documentid) {
		this.documentid = documentid;
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

	public List<SafetyWorker> getSafetyWorkers() {
		return this.safetyWorkers;
	}

	public void setSafetyWorkers(List<SafetyWorker> safetyWorkers) {
		this.safetyWorkers = safetyWorkers;
	}

	public SafetyWorker addSafetyWorker(SafetyWorker safetyWorker) {
		getSafetyWorkers().add(safetyWorker);
		safetyWorker.setSafetyMaterial(this);

		return safetyWorker;
	}

	public SafetyWorker removeSafetyWorker(SafetyWorker safetyWorker) {
		getSafetyWorkers().remove(safetyWorker);
		safetyWorker.setSafetyMaterial(null);

		return safetyWorker;
	}

	public SafetyMaterialType getSafetyMaterialType() {
		return this.safetyMaterialType;
	}

	public void setSafetyMaterialType(SafetyMaterialType safetyMaterialType) {
		this.safetyMaterialType = safetyMaterialType;
	}

}