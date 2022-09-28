package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the budget_type database table.
 * 
 */
@Entity
@Table(name="budget_type")
@NamedQuery(name="BudgetType.findAll", query="SELECT b FROM BudgetType b")
public class BudgetType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String name;

	public BudgetType() {
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