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

import avh.nufm.business.model.ConfirmationToken;
import avh.nufm.business.model.NufmRole;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserRole;
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
		ucImpl.removeRoleFromUser(contractor, SecurityCte.RoleContractor);
		ucImpl.deleteAllUserSpecs(contractor);
		List<ConfirmationToken> ctList = repo.getConfirmationTokenRepo().findByUserId(contractorId);
		repo.getConfirmationTokenRepo().deleteAll(ctList);
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
	public NufmUser updateContractor(NufmUser contractor,List<String> specializations) {
		NufmUser res = repo.getNfuserrepo().findByEid(contractor.getEid());
		ucImpl.deleteAllUserSpecs(res);
		res = ucImpl.addSpecializations(res.getEid(), specializations);
		res.setFullName(contractor.getFullName());
		res.setPhone(contractor.getPhone());
		res.setNationalId(contractor.getNationalId());
		res.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return repo.getNfuserrepo().save(res);
	}
	
	@Transactional
	public NufmUser getContractorById(String contractorId) {
		NufmUser res = repo.getNfuserrepo().findByEid(contractorId);
		return res;
	}


	
	
	
}
