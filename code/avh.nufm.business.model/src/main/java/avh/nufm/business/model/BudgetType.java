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
	private String type;

	public BudgetType() {
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}