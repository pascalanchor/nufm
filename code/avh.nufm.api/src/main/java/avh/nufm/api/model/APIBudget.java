package avh.nufm.api.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIBudget {
	private Integer difference;
	private Integer estimation;
	private Integer incomeTax;
	private Integer month;
	private String name;
	private Timestamp submissionDate;
	private Integer year;
	private String facilityId;
	private String typeId;
}
