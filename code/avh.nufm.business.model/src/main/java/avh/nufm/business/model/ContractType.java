package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the contract_type database table.
 * 
 */
@Entity
@Table(name="contract_type")
@NamedQuery(name="ContractType.findAll", query="SELECT c FROM ContractType c")
public class ContractType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String name;

	public ContractType() {
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