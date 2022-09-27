package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the nufm_role database table.
 * 
 */
@Entity
@Table(name="nufm_role")
@NamedQuery(name="NufmRole.findAll", query="SELECT n FROM NufmRole n")
public class NufmRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String description;

	private String name;

	public NufmRole() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}