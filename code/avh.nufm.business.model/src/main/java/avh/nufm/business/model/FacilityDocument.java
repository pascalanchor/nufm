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

	//bi-directional many-to-one association to Facility
	@ManyToOne
	@JoinColumn(name="facility_id")
	private Facility facility;

	public FacilityDocument() {
	}

	public String getDocPath() {
		return this.docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public Facility getFacility() {
		return this.facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

}