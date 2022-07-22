package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.NufmRole;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserRole;
import avh.nufm.business.model.UserSpecialization;
import avh.nufm.business.model.repository.NufmRepos;
import avh.nufm.security.common.SecurityCte;

@Component
public class ContractorControllerImpl {
	@Autowired private NufmRepos repo;
	@Autowired PasswordEncoder pHasher;
	@Autowired UserControllerImpl ucImpl;
	@Autowired ConfirmationTokenControllerImpl ctImpl;
	
	//creates password using the name of user and random 4 digits next to it
	private String createPasswordFromName(String name) {
		String noSpacename = name.replaceAll("\\s", "");
		Random random = new Random();
		long fourDigits = random.nextInt(10000);
		System.out.println(noSpacename+fourDigits);
		return noSpacename+fourDigits;
	}
	
	
	
	@Transactional
	public String createContractor(NufmUser contractor) {
		//check if user already exists
		Optional<NufmUser> ou = repo.getNfuserrepo().findById(contractor.getEid());
		if (ou.isPresent())
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("the user %s is already registered", contractor.getEid()));
		//continue filling the contractor data
		contractor.setEnabled(false);
		contractor.setCreatedAt(new Timestamp(System.currentTimeMillis()));    		
		contractor.setUpdatedAt(new Timestamp(System.currentTimeMillis())); 
		String pwd = createPasswordFromName(contractor.getFullName());
		contractor.setPassword(pHasher.encode(pwd));
		//save contractor to db
		repo.getNfuserrepo().save(contractor);
		return pwd;
	}
	
	@Transactional
	public void deleteContractor(String contractorId) {
		NufmUser contractor = repo.getNfuserrepo().findByEid(contractorId);
		repo.getNfuserrepo().delete(contractor);	
	}
	
	@Transactional 
	public List<NufmUser> getContractors() {
		NufmRole contractorRole = repo.getNfrolerepo().findById(SecurityCte.RoleContractor).get();
		List<UserRole> ur = repo.getUserrolerepo().findByNufmRole(contractorRole);
		List<NufmUser> res = new ArrayList<>();
		ur.stream().forEach(e-> res.add(e.getNufmUser()));
		return res;
	}
	
	@Transactional
	public void updateContractor(NufmUser contractorUpdate) {
		//continue filling the contractor data  	
		NufmUser contractor = repo.getNfuserrepo().findByEid(contractorUpdate.getEid());
		contractorUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		contractorUpdate.setPassword(contractor.getPassword());
		contractorUpdate.setCreatedAt(contractor.getCreatedAt());
		contractorUpdate.setEnabled(true);
		//save contractor to db
		repo.getNfuserrepo().save(contractorUpdate);
		List<UserSpecialization> specList = repo.getUserSpecrepo().findByNufmUser(contractor);
		repo.getUserSpecrepo().deleteAll(specList);
		return;
	}
	
	@Transactional
	public NufmUser getContractorById(String contractorId) {
		Optional<NufmUser> res = repo.getNfuserrepo().findById(contractorId);
		if(res.isPresent())
		{List<UserRole> list = repo.getUserrolerepo().findByNufmUser(res.get());
		List<String> roles = new ArrayList<>();
		list.stream().forEach(e->roles.add(e.getNufmRole().getName()));
		if(roles.contains(SecurityCte.RoleContractor))
			{return res.get();}
		else {throw new BusinessException(String.format("user %s is not a contractor", res.get().getFullName()));}
		}
		else throw new BusinessException(String.format("user does not exist 1"));
	}

	
	
	
	
}
