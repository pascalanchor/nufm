package avh.nufm.business.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the invoice database table.
 * 
 */
@Entity
@NamedQuery(name="Invoice.findAll", query="SELECT i FROM Invoice i")
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eid;

	private Timestamp date;

	@Column(name="deliver_to")
	private String deliverTo;

	private String description;

	@Column(name="invoice_to")
	private String invoiceTo;

	@Column(name="invoice_total")
	private double invoiceTotal;

	private double price;

	private double quantity;

	@Column(name="sales_tax")
	private double salesTax;

	private String status;

	@Column(name="sub_total")
	private double subTotal;

	public Invoice() {
	}

	public String getEid() {
		return this.eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getDeliverTo() {
		return this.deliverTo;
	}

	public void setDeliverTo(String deliverTo) {
		this.deliverTo = deliverTo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInvoiceTo() {
		return this.invoiceTo;
	}

	public void setInvoiceTo(String invoiceTo) {
		this.invoiceTo = invoiceTo;
	}

	public double getInvoiceTotal() {
		return this.invoiceTotal;
	}

	public void setInvoiceTotal(double invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getSalesTax() {
		return this.salesTax;
	}

	public void setSalesTax(double salesTax) {
		this.salesTax = salesTax;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getSubTotal() {
		return this.subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

}