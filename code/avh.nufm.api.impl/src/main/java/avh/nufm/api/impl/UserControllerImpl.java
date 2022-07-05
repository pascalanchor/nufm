package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.api.impl.logic.Especialization;
import avh.nufm.business.model.NufmRole;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserRole;
import avh.nufm.business.model.UserSpecialization;
import avh.nufm.business.model.repository.NufmRepos;
import avh.nufm.security.common.SecurityCte;

@Component
public class UserControllerImpl {
	@Autowired private NufmRepos repo;
	@Autowired PasswordEncoder pHasher;
	
	@Transactional
	public NufmUser createUser(NufmUser u) {
		if(u.getFullName().equals(""))
			throw new BusinessException("user name cannot be null !!");
		//check if duplicated name
		List<NufmUser> ulist=repo.getNfuserrepo().findByFullName(u.getFullName());
		if((ulist.size()>0)&&(ulist!=null))
			throw new BusinessException(String.format("the user name :%s is already exist", u.getFullName()));
		//check the specialization here
		List<UserSpecialization> speclist=u.getUserSpecializations();
		if((speclist.size()<=0) || speclist==null)
			throw new BusinessException("you must set the user specializations !! ");
		//check the specialization format
		Especialization espec=Especialization.fromString(speclist.get(0).getSpecialization().getName());
		if(espec==null)
			throw new BusinessException(String.format("invalid specialization format :%s", speclist.get(0).getSpecialization().getName()));
		
		repo.getNfuserrepo().save(u);
		return u;
	}

	@Transactional
	public NufmUser getUser(String userId) {
		NufmUser user=repo.getNfuserrepo().findByEid(userId);
		if(user==null) {
			return null;
		}
		return user;
	}

	@Transactional
	public Optional<NufmUser> getUser2(String userId) {
		Optional<NufmUser> user=repo.getNfuserrepo().findById(userId);
		if(user==null) {
			return null;
		}
		return user;
	}
	@Transactional
	public NufmUser updateUser2(NufmUser user, String userId, Timestamp timestamp) {
		user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
		user.setEid(userId);
		user.setCreatedAt(timestamp);
		repo.getNfuserrepo().save(user);
		return user;
	}

	public NufmUser updateUser(NufmUser user,ArrayList<String> roles) {
		//encode the password
		user.setPassword(pHasher.encode(user.getPassword()));	
		repo.getNfuserrepo().save(user);
		//delete user memberships
		List<UserRole> userMemberships = repo.getUserrolerepo().findByNufmUser(user);
		for(UserRole membership : userMemberships) {
			repo.getUserrolerepo().delete(membership);
		}
		for(String r : roles){
		Optional<NufmRole> oRole = repo.getNfrolerepo().findById(r);
		if(oRole.isEmpty())
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("the role %s cannot be found", SecurityCte.RoleAdmin));
		NufmRole role = oRole.get();
		UserRole mb = new UserRole();
		mb.setEid(UUID.randomUUID().toString());
		mb.setNufmRole(role);
		mb.setNufmUser(user);   		
		repo.getUserrolerepo().save(mb);}
		return user;
	}

	@Transactional
	public void deleteUser(NufmUser user) {
		repo.getNfuserrepo().deleteById(user.getEid());
	}

	public void updateUserEnabled(NufmUser user) {
		user.setEnabled(true);
		repo.getNfuserrepo().save(user);
	}

	public Optional<NufmRole> getRole(String id) {
		Optional<NufmRole> role=repo.getNfrolerepo().findById(id);
		if(role==null) {
			return null;
		}
		return role;
	}

	public void saveMembership(UserRole mb) {
		repo.getUserrolerepo().save(mb);
	}
}
