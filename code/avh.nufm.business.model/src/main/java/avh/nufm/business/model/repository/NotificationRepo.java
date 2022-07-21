package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Notification;
@Repository
public interface NotificationRepo extends CrudRepository<Notification, String> {
	public List<Notification> findByReceiverId(String receiverId);
	public List<Notification> findBySenderId(String senderId);
}
