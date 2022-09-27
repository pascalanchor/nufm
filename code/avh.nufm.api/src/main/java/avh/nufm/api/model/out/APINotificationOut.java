package avh.nufm.api.model.out;

import java.sql.Timestamp;

import avh.nufm.api.model.APINotification;
import avh.nufm.business.model.NufmUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class APINotificationOut extends APINotification{
	private String eid;
	private NufmUser sender;
	private String senderName;
	private Timestamp creationDate;
}
