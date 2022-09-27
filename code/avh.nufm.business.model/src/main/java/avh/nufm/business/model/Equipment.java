package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the equipment database table.
 * 
 */
@Entity
@NamedQuery(name="Equipment.findAll", query="SELECT e FROM Equipment e")
public class Equipment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private String description;

	private String location;

	private String name;

	@Column(name="number_of_items")
	private Integer numberOfItems;

	@Column(name="purchase_date")
	private String purchaseDate;

	@Column(name="type_id")
	private String typeId;

	@Column(name="unit_cost")
	private double unitCost;

	@Column(name="vendor_id")
	private String vendorId;

	@Column(name="zip_code")
	private String zipCode;

	public Equipment() {
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

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumberOfItems() {
		return this.numberOfItems;
	}

	public void setNumberOfItems(Integer numberOfItems) {
		this.numberOfItems = numberOfItems;
	}

	public String getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public double getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public String getVendorId() {
		return this.vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

}