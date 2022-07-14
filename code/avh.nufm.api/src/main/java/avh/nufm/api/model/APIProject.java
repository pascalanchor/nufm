package avh.nufm.api.model;


import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIProject {
private String facility_id;
private String contractor_id;
private String document_id;
private String name;
private Date dateFrom;
private Date dateTo;
private String status;
private String comment;
}