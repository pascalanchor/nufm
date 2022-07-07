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
public class WorkerControllerImpl {
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
	public String createWorker(NufmUser worker) {
		//check if user already exists
		Optional<NufmUser> ou = repo.getNfuserrepo().findById(worker.getEid());
		if (ou.isPresent())
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, String.format("the user %s is already registered", worker.getEid()));
		//continue filling the worker data
		worker.setEnabled(false);
		worker.setCreatedAt(new Timestamp(System.currentTimeMillis()));    		
		worker.setUpdatedAt(new Timestamp(System.currentTimeMillis())); 
		String pwd = createPasswordFromName(worker.getFullName());
		worker.setPassword(pHasher.encode(pwd));
		//save worker to db
		repo.getNfuserrepo().save(worker);
		return pwd;
	}
	
	@Transactional
	public void deleteWorker(String workerId) {
		NufmUser worker = repo.getNfuserrepo().findByEid(workerId);
		ucImpl.removeRoleFromUser(worker, SecurityCte.RoleWorker);
		ucImpl.deleteAllUserSpecs(worker);
		List<ConfirmationToken> ctList = repo.getConfirmationTokenRepo().findByUserId(workerId);
		repo.getConfirmationTokenRepo().deleteAll(ctList);
		repo.getNfuserrepo().delete(worker);	
	}
	
	@Transactional 
	public List<NufmUser> getWorkers() {
		NufmRole workerRole = repo.getNfrolerepo().findById(SecurityCte.RoleWorker).get();
		List<UserRole> ur = repo.getUserrolerepo().findByNufmRole(workerRole);
		List<NufmUser> res = new ArrayList<>();
		ur.stream().forEach(e-> res.add(e.getNufmUser()));
		return res;
	}
	
	@Transactional
	public NufmUser updateWorker(NufmUser worker,List<String> specializations) {
		NufmUser res = repo.getNfuserrepo().findByEid(worker.getEid());
		ucImpl.deleteAllUserSpecs(res);
		res = ucImpl.addSpecializations(res.getEid(), specializations);
		res.setFullName(worker.getFullName());
		res.setPhone(worker.getPhone());
		res.setNationalId(worker.getNationalId());
		res.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		return repo.getNfuserrepo().save(res);
	}
	
	@Transactional
	public NufmUser getWorkerById(String workerId) {
		NufmUser res = repo.getNfuserrepo().findByEid(workerId);
		return res;
	}


	
	
	
}