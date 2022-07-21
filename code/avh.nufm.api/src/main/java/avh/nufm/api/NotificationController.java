package avh.nufm.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.common.PathCte;
import avh.nufm.api.impl.NotificationControllerImpl;
import avh.nufm.api.model.in.APINotificationIn;
import avh.nufm.api.model.out.APINotificationOut;
import avh.nufm.api.model.transformer.NotificationTransformer;
import avh.nufm.business.model.Notification;
@RestController
public class NotificationController {
	@Autowired private NotificationControllerImpl nci;
	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR','OCCUPANT','WORKER')")
	@PostMapping(PathCte.AddNotificationServletPath)
	public ResponseEntity<String> sendNotification(@RequestBody APINotificationIn notificationIn,Authentication auth){
		try {
			Notification res = NotificationTransformer.ModelFromNotification(notificationIn);
			System.out.println(res.getReceiverId());
			nci.saveNotification(res, auth.getName());
			return ResponseEntity.ok().body("notification sent");
		}catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
    	}
	}
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR','OCCUPANT','WORKER')")
	@GetMapping(PathCte.GetNotificationsByIdServletPath)
	public ResponseEntity<List<APINotificationOut>> getNotifications(Authentication auth){
		try {
			
			List<Notification> list = nci.getReceivedNotifications(auth.getName());
			List<APINotificationOut> res = new ArrayList<>();
			list.stream().forEach(e->res.add(NotificationTransformer.NotificationFromModel(e)));
			return ResponseEntity.ok().body(res);
			
		}catch (Exception e) {
    		throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
    	}
	}
}
