package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.FacilityType;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class FacilityControllerImpl {
	@Autowired
	private NufmRepos repo;

	@Transactional
	public Facility createFacility(Facility fc) {
		if (fc.getName().equals(""))
			throw new BusinessException("the facility name cannot be null");
		// check if the name already exists
		for (Facility itr : repo.getFacrepo().findAll()) {
			if (itr.getName().equals(fc.getName()))
				throw new BusinessException("facility name : " + fc.getName() + " already exists !!");
		}

		// generate new random id
		fc.setEid(UUID.randomUUID().toString());
		// set the date
		fc.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

		// the user entity here only contains user iid
		// we need to get the entire user entity and assign it again to the facility
		// foreign key
		Optional<NufmUser> u = repo.getNfuserrepo().findById(fc.getNufmUser().getEid());
		if (u == null || u.isEmpty())
			throw new BusinessException("error user id !!");
		fc.setNufmUser(u.get());
		// find and fill the facility
		if (fc.getFacility() == null) {
			fc.setFacility(null);
		} else {
			Optional<Facility> parent = repo.getFacrepo().findById(fc.getFacility().getEid());
			if(parent.isPresent()) {
				fc.setFacility(parent.get());
			}
			else
				throw new BusinessException("parent facility does not exist");			
		}
		// find and fill facility type
		Optional<FacilityType> ft = repo.getFactyperepo().findById(fc.getFacilityType().getEid());
		if(ft.isPresent())
			fc.setFacilityType(ft.get());
		else
			throw new BusinessException("facility type does not exist");	
		//save to DB
		repo.getFacrepo().save(fc);
		return fc;
	}

	public Facility getFacilityById(String fid) {
		if (fid == null || fid == "")
			throw new BusinessException("facility id must not be null !!");
		Optional<Facility> flist = repo.getFacrepo().findById(fid);
		if (flist.isEmpty())
			throw new BusinessException(String.format("there is no facility with this id:%s", fid));
		Facility fac = flist.get();

		return fac;
	}

	@Transactional
	public Facility updateFacility(String id, Facility facilityUpdate) {
		// get the old facility by id
		Optional<Facility> flist = repo.getFacrepo().findById(id);
		if (flist.isEmpty())
			throw new BusinessException(String.format("the facility id:%s is not valid ", id));
		Facility res = flist.get();
		if (facilityUpdate.getName().equals(""))
			throw new BusinessException("the facility name cannot be null");

		// assign new values
		res.setName(facilityUpdate.getName());
		res.setLocation(facilityUpdate.getLocation());
		Optional<NufmUser> u = repo.getNfuserrepo().findById(facilityUpdate.getNufmUser().getEid());
		if (u == null || u.isEmpty())
			throw new BusinessException("error user id !!");
		res.setNufmUser(u.get());
		// find and fill the facility
		if (res.getFacility() == null) {
			res.setFacility(null);
		} else {
			Optional<Facility> parent = repo.getFacrepo().findById(facilityUpdate.getFacility().getEid());
			if(parent.isPresent()) {
				res.setFacility(parent.get());
			}
			else
				throw new BusinessException("parent facility does not exist");			
		}
		// find and fill facility type
		Optional<FacilityType> ft = repo.getFactyperepo().findById(facilityUpdate.getFacilityType().getEid());
		if(ft.isPresent())
			res.setFacilityType(ft.get());
		else
			throw new BusinessException("facility type does not exist");	

		// now we can save the updated facility to database
		return repo.getFacrepo().save(res);

	}

	public List<Facility> getAllFacilities() {
		List<Facility> res = 
				  StreamSupport.stream(repo.getFacrepo().findAll().spliterator(), false)
				    .collect(Collectors.toList());
		return res;
	}
}
