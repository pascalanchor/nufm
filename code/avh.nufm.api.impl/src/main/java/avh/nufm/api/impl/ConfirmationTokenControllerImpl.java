package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import avh.nufm.business.model.ConfirmationToken;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class ConfirmationTokenControllerImpl {
	@Autowired private NufmRepos repo;

	@Transactional
	public String createConfirmationToken(String userId) {
		ConfirmationToken ct = new ConfirmationToken();
		NufmUser user = repo.getNfuserrepo().findByEid(userId);
		ct.setIid(UUID.randomUUID().toString());
		ct.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		ct.setExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
		ct.setNufmUser(user);
		String tok = UUID.randomUUID().toString();
		ct.setToken(tok);
		repo.getConfirmationTokenRepo().save(ct);
		return ct.getToken();
	}
	

	
	@Transactional
	public ConfirmationToken saveToken(ConfirmationToken ct) {
		ct.setIid(UUID.randomUUID().toString());
		ct.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
		ct.setExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)));
		repo.getConfirmationTokenRepo().save(ct);
		return ct;
	}
	
	@Transactional
	public ConfirmationToken getToken(String token) {
		ConfirmationToken contoken = repo.getConfirmationTokenRepo().findByToken(token);
		if(token.equals(null)) {
			return null;
		}
		return contoken;
	}

	@Transactional
	public void updateToken(ConfirmationToken ct, String iid, String token, Timestamp createdAt) {
		ct.setIid(iid);
		ct.setToken(token);
		ct.setCreatedAt(createdAt);
		ct.setConfirmedAt(Timestamp.valueOf(LocalDateTime.now()));
		repo.getConfirmationTokenRepo().save(ct);
	}
	
	@Transactional 
	public void deleteToken(String id) {
		ConfirmationToken ct = repo.getConfirmationTokenRepo().findByIid(id);
		repo.getConfirmationTokenRepo().delete(ct);
	}

}
