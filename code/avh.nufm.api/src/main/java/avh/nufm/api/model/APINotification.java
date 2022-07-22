package avh.nufm.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APINotification {
	private String receiverId;
	private String taskName;
	private String taskType;
	private String taskStatus;
	private String facilityName;
	private Date taskDate;
	private Date deliveryDate;
	private String comment;
}