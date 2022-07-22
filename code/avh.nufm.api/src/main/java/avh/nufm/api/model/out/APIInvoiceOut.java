package avh.nufm.api.model.out;

import java.sql.Timestamp;

import avh.nufm.api.model.APIInvoice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIInvoiceOut extends APIInvoice{
private String eid;
private String Status;
private Timestamp createdAt;
}
