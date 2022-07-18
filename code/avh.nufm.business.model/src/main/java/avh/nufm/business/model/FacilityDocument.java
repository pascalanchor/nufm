package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the facility_document database table.
 * 
 */
@Entity
@Table(name="facility_document")
@NamedQuery(name="FacilityDocument.findAll", query="SELECT f FROM FacilityDocument f")
public class FacilityDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="doc_path")
	private String docPath;

	@Column(name="facility_id")
	private String facilityId;

	public FacilityDocument() {
	}

	public String getDocPath() {
		return this.docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

}