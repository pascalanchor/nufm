package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the budget database table.
 * 
 */
@Entity
@NamedQuery(name="Budget.findAll", query="SELECT b FROM Budget b")
public class Budget implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iid;

	private Integer difference;

	private Integer estimation;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="income_tax")
	private Integer incomeTax;

	private Integer month;

	private String name;

	@Column(name="submission_date")
	private Timestamp submissionDate;

	@Column(name="type_id")
	private String typeId;

	private Integer year;

	public Budget() {
	}

	public String getIid() {
		return this.iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public Integer getDifference() {
		return this.difference;
	}

	public void setDifference(Integer difference) {
		this.difference = difference;
	}

	public Integer getEstimation() {
		return this.estimation;
	}

	public void setEstimation(Integer estimation) {
		this.estimation = estimation;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Integer getIncomeTax() {
		return this.incomeTax;
	}

	public void setIncomeTax(Integer incomeTax) {
		this.incomeTax = incomeTax;
	}

	public Integer getMonth() {
		return this.month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getSubmissionDate() {
		return this.submissionDate;
	}

	public void setSubmissionDate(Timestamp submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}