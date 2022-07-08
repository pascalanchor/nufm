package avh.nufm.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APITask {
private String facility_id;
private String type_id;
private String project_id;
private String name;
private Date   date_from;
private Date   date_to;
private String status;
private String comment;
private String document_id;
}
