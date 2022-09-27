package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.ConfirmationToken;
import avh.nufm.business.model.NufmRole;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserRole;
import avh.nufm.business.model.UserSpecialization;
import avh.nufm.business.model.repository.NufmRepos;
import avh.nufm.security.common.SecurityCte;

@Component
public class OccupantControllerImpl {
	@Autowired private NufmRepos repo;
	@Autowired PasswordEncoder pHasher;
	@Autowired UserControllerImpl ucImpl;
	@Autowired ConfirmationTokenControllerImpl ctImpl;
	
	@Transactional 
	public NufmUser occupantExists(String id) {
		//check if user already exists
		Optional<NufmUser> ou = repo.getNfuserrepo().findById(id);
		if (ou.isPresent())
			throw new BusinessException("occupant is in a facility already");
		else 
			return null;
	}
	
	@Transactional
	public String createOccupant(NufmUser occupant,String imagePath) {
		//continue filling the occupant data
		occupant.setEnabled(false);
		occupant.setCreatedAt(new Timestamp(System.currentTimeMillis()));    		
		occupant.setUpdatedAt(new Timestamp(System.currentTimeMillis())); 
		occupant.setProfileImage(imagePath);
		String pwd = ucImpl.createPasswordFromName(occupant.getFullName());
		occupant.setPassword(pHasher.encode(pwd));
		//save occupant to db
		repo.getNfuserrepo().save(occupant);
		return pwd;
	}
	
	@Transactional
	public void updateOccupant(NufmUser occupantUpdate,String imagePath) {
		//continue filling the occupant data  	
		NufmUser occupant = repo.getNfuserrepo().findByEid(occupantUpdate.getEid());
		occupantUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		occupantUpdate.setPassword(occupant.getPassword());
		occupantUpdate.setCreatedAt(occupant.getCreatedAt());
		occupantUpdate.setEnabled(true);
		occupantUpdate.setProfileImage(imagePath);
		//save occupant to db
		repo.getNfuserrepo().save(occupantUpdate);
		List<UserSpecialization> specList = repo.getUserSpecrepo().findByNufmUser(occupant);
		repo.getUserSpecrepo().deleteAll(specList);
		return;
	}
	
	@Transactional
	public void deleteOccupant(String occupantId) {
		NufmUser occupant = repo.getNfuserrepo().findByEid(occupantId);
		ucImpl.removeRoleFromUser(occupant, SecurityCte.RoleOccupant);
		ucImpl.deleteAllUserSpecs(occupant);
		List<ConfirmationToken> ctList = repo.getConfirmationTokenRepo().findByNufmUser(occupant);
		repo.getConfirmationTokenRepo().deleteAll(ctList);
		repo.getNfuserrepo().delete(occupant);	
	}
	
	@Transactional 
	public List<NufmUser> getOccupants() {
		NufmRole occupantRole = repo.getNfrolerepo().findById(SecurityCte.RoleOccupant).get();
		List<UserRole> ur = repo.getUserrolerepo().findByNufmRole(occupantRole);
		List<NufmUser> res = new ArrayList<>();
		ur.stream().forEach(e-> res.add(e.getNufmUser()));
		return res;
	}
	
	@Transactional
	public NufmUser updateOccupant(NufmUser occupant,List<String> specializations) {
		NufmUser res = repo.getNfuserrepo().findByEid(occupant.getEid());
		ucImpl.deleteAllUserSpecs(res);
		res = ucImpl.addSpecializations(res.getEid(), specializations);
		res.setFullName(occupant.getFullName());
		res.setPhone(occupant.getPhone());
		res.setProfileImage(occupant.getProfileImage());
		res.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return repo.getNfuserrepo().save(res);
	}
	
	@Transactional
	public NufmUser getOccupantById(String occupantId) {
		NufmUser res = repo.getNfuserrepo().findByEid(occupantId);
		List<String> roles = new ArrayList<>();
		List<UserRole> urole = repo.getUserrolerepo().findByNufmUser(res);
		urole.stream().forEach(e->roles.add(e.getNufmRole().getName()));
		if(roles.contains(SecurityCte.RoleOccupant))
			{return res;}
		else {throw new BusinessException(String.format("user %s is not an occupant", res.getFullName()));}
	}
	


	
	
	
}
