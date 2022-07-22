package avh.nufm.api.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIInvoice {
private String invoiceTo;
private String deliverTo;
private double quantity;
private double price;
private String description;
private double subTotal;
private double salesTax;
private double invoiceTotal;
private String status;
}
