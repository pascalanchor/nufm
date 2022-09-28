package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Notification;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class NotificationControllerImpl {
	@Autowired private NufmRepos rep;
	public void saveNotification(Notification n, String senderId) {
		try {
		n.setEid(UUID.randomUUID().toString());
		n.setCreationDate(new Timestamp(System.currentTimeMillis()));
		NufmUser user = rep.getNfuserrepo().findByEid(senderId);
		n.setSender(user);
		n.setSenderName(user.getFullName());
		rep.getNotificationRepo().save(n);
}
		catch(Exception e) {
			throw new BusinessException("notification was not sent");
		}
	}
	
	public List<Notification> getSendedNotifications(String userId){
		NufmUser user=rep.getNfuserrepo().findByEid(userId);
		if(user==null) {
			throw new BusinessException("user was not found");
		}
		return rep.getNotificationRepo().findBySender(user);
	}
	
	public List<Notification> getReceivedNotifications(String userId){
		NufmUser user=rep.getNfuserrepo().findByEid(userId);
		if(user==null) {
			throw new BusinessException("user was not found");
		}
		return rep.getNotificationRepo().findByReceiver(user);
	}
}
