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

	@Column(name="income_tax")
	private Integer incomeTax;

	private Integer month;

	private String name;

	@Column(name="submission_date")
	private Timestamp submissionDate;

	private Integer year;

	//uni-directional many-to-one association to BudgetType
	@ManyToOne
	@JoinColumn(name="type")
	private BudgetType budgetType;

	//uni-directional many-to-one association to Facility
	@ManyToOne
	@JoinColumn(name="facility_id")
	private Facility facility;

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

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BudgetType getBudgetType() {
		return this.budgetType;
	}

	public void setBudgetType(BudgetType budgetType) {
		this.budgetType = budgetType;
	}

	public Facility getFacility() {
		return this.facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

}