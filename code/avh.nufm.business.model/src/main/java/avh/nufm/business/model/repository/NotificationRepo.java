package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.Notification;
import avh.nufm.business.model.NufmUser;
@Repository
public interface NotificationRepo extends CrudRepository<Notification, String> {
	public List<Notification> findBySender(NufmUser sender);
	public List<Notification> findByReceiver(NufmUser receiver);
}
