package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class FacilityControllerImpl {
	@Autowired private NufmRepos repo;
	
	@Transactional
	public Facility createFacility(Facility fc) {
		if(fc.getName().equals(""))
			throw new BusinessException("the facility name cannot be null");
		//check if the name already exists
		for (Facility itr : repo.getFacrepo().findAll()) {
			if(itr.getName().equals(fc.getName()))
				throw new BusinessException("facility name :"+fc.getName()+" is already exists !!");
		}
		
		//generate new random id 
		fc.setEid(UUID.randomUUID().toString());
		//set the date 
		fc.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
		
		//the user entity here only contains user iid
		//we need to get the entire user entity and assign it again to the facility foreign key
		 Optional<NufmUser> u = repo.getNfuserrepo().findById(fc.getNufmUser().getEid());
		if(u==null || u.isEmpty())
			throw new BusinessException("error user id !!");
		fc.setNufmUser(u.get());
		
		//now save to DB
		repo.getFacrepo().save(fc);
		return fc;
		
	}
	
	
	public Facility getFacilityById(String fid) {
		if(fid==null || fid=="")
			throw new BusinessException("facility id must not be null !!");
		Optional<Facility> flist=repo.getFacrepo().findById(fid);
		if(flist.isEmpty())
			throw new BusinessException(String.format("there is no facility with this id:%s", fid));
		Facility fac=flist.get();
		
		return fac;
	}
	
	@Transactional
	public Facility updateFacility(String fid,Facility newFacility) {
		//get the old facility by id
		Optional<Facility> flist=repo.getFacrepo().findById(fid);
		if(flist.isEmpty())
			throw new BusinessException(String.format("the facility id:%s is not valid ",fid));
		Facility oldfacility=flist.get();
		if(newFacility.getName().equals(""))
			throw new BusinessException("the facility name cannot be null");
		//check if the name already exists
		for (Facility itr : repo.getFacrepo().findAll()) {
			if(itr.getName().equals(newFacility.getName()) && !itr.getEid().equals(fid))
				throw new BusinessException(String.format("facility name :%s is already exists !!",newFacility.getName()));
		}
		
		//assign new values 
		oldfacility.setName(newFacility.getName());
		
		//now we can save the updated facility to database
		repo.getFacrepo().save(oldfacility);
		
		return oldfacility;
		
	}
}
