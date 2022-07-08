package avh.nufm.api.model;

import java.security.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIInvoice {
private String invoice_to;
private String deliver_to;
private double quantity;
private double price;
private String description;
private double sub_total;
private double sales_tax;
private double invoice_total;
private String status;
private Timestamp date;
}
