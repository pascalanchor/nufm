package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the facility_type database table.
 * 
 */
@Entity
@Table(name="facility_type")
@NamedQuery(name="FacilityType.findAll", query="SELECT f FROM FacilityType f")
public class FacilityType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String name;

	public FacilityType() {
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