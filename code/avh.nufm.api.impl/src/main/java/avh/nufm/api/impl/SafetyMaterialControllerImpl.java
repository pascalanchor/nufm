package avh.nufm.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.api.impl.logic.EMaterialStatus;
import avh.nufm.business.model.SafetyMaterial;
import avh.nufm.business.model.SafetyMaterialType;
import avh.nufm.business.model.SafetyWorker;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class SafetyMaterialControllerImpl {
@Autowired NufmRepos repo;
@Autowired SafetyWorkerControllerImpl sftwrkImp;


@Transactional
public SafetyMaterial addSafetyMaterial(SafetyMaterial sft,List<String> workerNames) {
	//check the name
	if(sft.getName()==null || sft.getName().equals(""))
		throw new BusinessException("the safety material name cannot be empty !!");
	//check type
	Optional<SafetyMaterialType> mtrtype=repo.getSafmtrTyperepo().findById(sft.getSafetyMaterialType().getEid());
	if(mtrtype==null || mtrtype.isEmpty())
		throw new BusinessException(String.format("invalid safety material type id:%s", sft.getSafetyMaterialType().getEid()));
	//check the status
	EMaterialStatus sts=EMaterialStatus.fromString(sft.getStatus());
	if(sts==null)
		throw new BusinessException(String.format("invalid status format :%s", sft.getStatus()));
	
	//complete the safetyMaterial definitions
	sft.setEid(UUID.randomUUID().toString());
	//we can save this material to database then assign the workers to it
	repo.getSafmtrrepo().save(sft);
	
	for(String wrkId:workerNames) {
		sftwrkImp.assignWorkerToMaterial(sft, wrkId);
	}
	
	return sft;
}



public Iterable<SafetyMaterial> getAllSafetyMaterials(){
	return repo.getSafmtrrepo().findAll();
}


@Transactional
public SafetyMaterial updateSafetyMaterial(String sftId,SafetyMaterial newSafetyMaterial,List<String> workerNames) {
	//check the sftId
	Optional<SafetyMaterial> sftOp=repo.getSafmtrrepo().findById(sftId);
	if(sftOp==null || sftOp.isEmpty())
		throw new BusinessException(String.format("invalid safetyMaterial Id:%s", sftId));
	SafetyMaterial updatedSft=sftOp.get();
	
	//--------------check the new details -----------
	//check the name
	if(newSafetyMaterial.getName()==null || newSafetyMaterial.getName().equals(""))
		throw new BusinessException("the safety material name cannot be empty !!");
	//check type
	Optional<SafetyMaterialType> mtrtype=repo.getSafmtrTyperepo().findById(newSafetyMaterial.getSafetyMaterialType().getEid());
	if(mtrtype==null || mtrtype.isEmpty())
		throw new BusinessException(String.format("invalid safety material type id:%s", newSafetyMaterial.getSafetyMaterialType().getEid()));
	//check the status
	EMaterialStatus sts=EMaterialStatus.fromString(newSafetyMaterial.getStatus());
	if(sts==null)
		throw new BusinessException(String.format("invalid status format :%s", newSafetyMaterial.getStatus()));
		
	//complete the safetyMaterial definitions
	updatedSft.setName(newSafetyMaterial.getName());
	updatedSft.setStatus(newSafetyMaterial.getStatus());
	updatedSft.setSafetyMaterialType(newSafetyMaterial.getSafetyMaterialType());
	updatedSft.setDocumentId(newSafetyMaterial.getDocumentId());
	//we can save this material to database then assign the workers to it
	repo.getSafmtrrepo().save(updatedSft);
	//now we need to delete the old assigned workers
	//then assign new workers (it can be the same workers)
	
	List<SafetyWorker> oldworkers=repo.getSafworkrepo().findBySafetyMaterial(updatedSft);
	for(SafetyWorker itr:oldworkers) {
		repo.getSafworkrepo().delete(itr);
	}
	
	//now assign the new workers
	for(String wrkId:workerNames) {
		sftwrkImp.assignWorkerToMaterial(newSafetyMaterial, wrkId);
	}
	
	return updatedSft;
	
}

@Transactional
public Boolean deleteSafetyMaterial(String sftId) {
	//check the sftId
	Optional<SafetyMaterial> sftOp=repo.getSafmtrrepo().findById(sftId);
	if(sftOp==null || sftOp.isEmpty())
		throw new BusinessException(String.format("invalid safetyMaterial Id:%s", sftId));
	SafetyMaterial sft=sftOp.get();
	
	repo.getSafmtrrepo().delete(sft);
	Optional<SafetyMaterial> sftOptest=repo.getSafmtrrepo().findById(sftId);
	if(sftOptest==null || sftOptest.isEmpty())
		return true;
	return false;
}

}
