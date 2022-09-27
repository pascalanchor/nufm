package avh.nufm.api.model.transformer;

import avh.nufm.api.model.in.APINotificationIn;

import avh.nufm.api.model.out.APINotificationOut;
import avh.nufm.business.model.Notification;


public class NotificationTransformer {
	public static Notification ModelFromNotification(APINotificationIn in) {
		Notification res = new Notification();
		res.setReceiver(in.getReceiver());
		res.setTaskName(in.getTaskName());
		res.setTaskType(in.getTaskType());
		res.setTaskStatus(in.getTaskStatus());
		res.setTaskDate(in.getTaskDate());
		res.setFacilityName(in.getFacilityName());
		res.setDeliveryDate(in.getDeliveryDate());
		res.setComment(in.getComment());
		return res;
	}
	
	public static APINotificationOut NotificationFromModel(Notification notification) {
		APINotificationOut res = new APINotificationOut();
		res.setEid(notification.getEid());
		res.setSender(notification.getSender());
		res.setSenderName(notification.getSenderName());
		res.setReceiver(notification.getReceiver());
		res.setTaskName(notification.getTaskName());
		res.setTaskType(notification.getTaskType());
		res.setTaskStatus(notification.getTaskStatus());
		res.setTaskDate(notification.getTaskDate());
		res.setFacilityName(notification.getFacilityName());
		res.setDeliveryDate(notification.getDeliveryDate());
		res.setComment(notification.getComment());
		res.setCreationDate(notification.getCreationDate());
		return res;
	}
}
