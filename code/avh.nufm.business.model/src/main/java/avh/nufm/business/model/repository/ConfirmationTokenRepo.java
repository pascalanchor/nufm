package avh.nufm.business.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import avh.nufm.business.model.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepo extends CrudRepository<ConfirmationToken, String>{
	public ConfirmationToken findByIid(String iid);
	public ConfirmationToken findByToken(String token);
	public List<ConfirmationToken> findByUserId(String userId);
	
}