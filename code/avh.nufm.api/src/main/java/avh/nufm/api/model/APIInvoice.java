package avh.nufm.api.model;


import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIInvoice {
private String invoiceTo;
private String deliverTo;
private BigDecimal quantity;
private BigDecimal price;
private String description;
private BigDecimal subTotal;
private BigDecimal salesTax;
private BigDecimal invoiceTotal;
private String status;
}
