package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the project_worker database table.
 * 
 */
@Entity
@Table(name="project_worker")
@NamedQuery(name="ProjectWorker.findAll", query="SELECT p FROM ProjectWorker p")
public class ProjectWorker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	//bi-directional many-to-one association to NufmUser
	@ManyToOne
	@JoinColumn(name="worker_id")
	private NufmUser nufmUser;

	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;

	public ProjectWorker() {
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

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}