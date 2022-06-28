package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the safety_worker database table.
 * 
 */
@Entity
@Table(name="safety_worker")
@NamedQuery(name="SafetyWorker.findAll", query="SELECT s FROM SafetyWorker s")
public class SafetyWorker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	//bi-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="worker_id")
	private NufmUser nufmUser;

	//bi-directional many-to-one association to SafetyMaterial
	@ManyToOne
	@JoinColumn(name="material_id")
	private SafetyMaterial safetyMaterial;

	public SafetyWorker() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public NufmUser getNufmUser() {
		return this.nufmUser;
	}

	public void setNufmUser(NufmUser nufmUser) {
		this.nufmUser = nufmUser;
	}

	public SafetyMaterial getSafetyMaterial() {
		return this.safetyMaterial;
	}

	public void setSafetyMaterial(SafetyMaterial safetyMaterial) {
		this.safetyMaterial = safetyMaterial;
	}

}