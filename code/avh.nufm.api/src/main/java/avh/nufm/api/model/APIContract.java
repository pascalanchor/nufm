package avh.nufm.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIContract {
	private Integer creditPeriod;
	private Date endDate;
	private Date noticeDate;
	private String number;
	private String paymentMethod;
	private Date reviewDate;
	private Date startDate;
	private String supplier;
	private String template;
	private String title;
	private String typeId;
	private String description;
}
