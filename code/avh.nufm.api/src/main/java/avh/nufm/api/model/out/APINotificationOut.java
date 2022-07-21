package avh.nufm.api.model.out;

import java.sql.Timestamp;

import avh.nufm.api.model.APINotification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class APINotificationOut extends APINotification{
	private String eid;
	private String senderId;
	private String senderName;
	private Timestamp creationDate;
}
