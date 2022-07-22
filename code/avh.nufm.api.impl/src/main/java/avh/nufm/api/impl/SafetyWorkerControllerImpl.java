package avh.nufm.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.SafetyMaterial;
import avh.nufm.business.model.SafetyWorker;
import avh.nufm.business.model.UserRole;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class SafetyWorkerControllerImpl {
@Autowired NufmRepos repo;

public SafetyWorker assignWorkerToMaterial(SafetyMaterial sft,String workerName) {
	//check the material
	Optional<SafetyMaterial> sftOp=repo.getSafmtrrepo().findById(sft.getEid());
	if(sftOp==null || sftOp.isEmpty())
		throw new BusinessException(String.format("invalid material id :%s", sft.getEid()));
	
	//check worker
	List<NufmUser> users=repo.getNfuserrepo().findByFullName(workerName);
	if(users==null || users.isEmpty())
		throw new BusinessException(String.format("invalid user id :%s",workerName));
	NufmUser worker=users.get(0);
	//check the user role (must be ROLE_WORKER)
	List<UserRole> roles;
	roles=repo.getUserrolerepo().findByNufmUser(worker);
	boolean isworker=false;
	for(UserRole role:roles) {
		if(role.getNufmRole().getName().equals("ROLE_WORKER"))
			isworker=true;
	}
	if(!isworker)
		throw new BusinessException(String.format("the user with fullName:%s does not have a role worker!! ", worker.getFullName()));
	
	
	SafetyWorker sftwrk=new SafetyWorker();
	//fill all the attributes
	sftwrk.setEid(UUID.randomUUID().toString());
	sftwrk.setNufmUser(worker);
	sftwrk.setSafetyMaterial(sft);
	//save to the database
	repo.getSafworkrepo().save(sftwrk);
	return sftwrk;
}

public List<String> getWorkersForMaterial(String mtrId){
	List<String> res=new ArrayList<>();
	//get the material
	Optional<SafetyMaterial> sftOp=repo.getSafmtrrepo().findById(mtrId);
	if(sftOp==null || sftOp.isEmpty())
		throw new BusinessException(String.format("invalid material id :%s",mtrId));
	SafetyMaterial sft=sftOp.get();
	List<SafetyWorker> sftwrk=repo.getSafworkrepo().findBySafetyMaterial(sft);
	for(SafetyWorker itr:sftwrk) {
		res.add(itr.getNufmUser().getFullName());
	}
	return res;
}
	
}
