package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Document;
import avh.nufm.business.model.Equipment;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.FacilityEquipment;
import avh.nufm.business.model.FacilityType;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class FacilityControllerImpl {
	@Autowired
	private NufmRepos repo;
	@Autowired PasswordEncoder pHasher;
	
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
	
	@Transactional
	public List<FacilityEquipment> addEquipmentToFacility(String facilityId, List<String> equipmentIds) {
		List<FacilityEquipment> res = new ArrayList<>();
		Optional<Facility> facility = repo.getFacrepo().findById(facilityId);
		if(facility.isPresent()) {
		for(String e : equipmentIds) {
		Optional<Equipment> eqpmnt = repo.getEqmtrepo().findById(e);
		if(eqpmnt.isPresent()) {
			FacilityEquipment fe = new FacilityEquipment();
			fe.setEid(UUID.randomUUID().toString());
			fe.setEquipmentId(eqpmnt.get().getEid());
			fe.setFacilityId(facility.get().getEid());
			repo.getFacilityEquipmentRepo().save(fe);
			res.add(fe);
		}
		}
		return res;
		}
		return null;
	}
	
	@Transactional
	public List<FacilityEquipment> updateEquipmentOfFacility(String facilityId, List<String> equipmentIds) {
		List<FacilityEquipment> res = new ArrayList<>();
		Optional<Facility> facility = repo.getFacrepo().findById(facilityId);
		if(facility.isPresent()) {
			List<FacilityEquipment> oldEqpmnts = repo.getFacilityEquipmentRepo().findByFacility(facility.get());
			repo.getFacilityEquipmentRepo().deleteAll(oldEqpmnts);
		for(String e : equipmentIds) {
		Optional<Equipment> eqpmnt = repo.getEqmtrepo().findById(e);
		if(eqpmnt.isPresent()) {
			FacilityEquipment fe = new FacilityEquipment();
			fe.setEid(UUID.randomUUID().toString());
			fe.setEquipmentId(eqpmnt.get().getEid());
			fe.setFacilityId(facility.get().getEid());
			repo.getFacilityEquipmentRepo().save(fe);
			res.add(fe);
		}
		}
		return res;
		}
		return null;
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
	
	public List<String> getFacilityDocuments(String id){
		Optional<Facility> facility = repo.getFacrepo().findById(id);
		List<String> res = new ArrayList<>();
		if(facility.isPresent()) {
			List<Document> list = repo.getDocumentRepo().findByFacility(facility.get());
			list.stream().forEach(e-> res.add(e.getDocumentPath()));
			return res;
		}
		else 
			throw new BusinessException("facility does not exist");
	}

	@Transactional
	public Facility updateFacility(String id, Facility facilityUpdate) {
		// get the old facility by id
		Optional<Facility> oldFacility = repo.getFacrepo().findById(id);
		if (oldFacility.isEmpty())
			throw new BusinessException(String.format("the facility id:%s is not valid ", id));
		Facility res = oldFacility.get();
		if (facilityUpdate.getName().equals(""))
			throw new BusinessException("the facility name cannot be null");
		// check if the name already exists
		if(!facilityUpdate.getName().equals(res.getName()))
		{for (Facility itr : repo.getFacrepo().findAll()) {
			if (itr.getName().equals(facilityUpdate.getName()))
				throw new BusinessException("facility name : " + res.getName() + " already exists !!");
		}
		res.setName(facilityUpdate.getName());
		}
		res.setLocation(facilityUpdate.getLocation());
		// find and fill the facility
		if (facilityUpdate.getFacility() == null) {
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
		//save to DB
		repo.getFacrepo().save(res);
		return res;

	}

	public List<Facility> getAllFacilities() {
		List<Facility> res = 
				  StreamSupport.stream(repo.getFacrepo().findAll().spliterator(), false)
				    .collect(Collectors.toList());
		return res;
	}

	@Transactional
	public void addOccupantToFacility(String facilityId, String occupantId) {
		Optional<Facility> facility = repo.getFacrepo().findById(facilityId);
		Optional<NufmUser> occupant = repo.getNfuserrepo().findById(occupantId);
		if(facility.isPresent()) {
			if(occupant.isPresent()) {
				Facility res = facility.get();
				res.setNufmUser(occupant.get());
				repo.getFacrepo().save(res);
			}
			else
				throw new BusinessException("occupant was not registered");	
		}
		else
			throw new BusinessException("facility does not exist");		
		
	}
	
	@Transactional
	public void addFacilityDoc(String id, String docPath) {
		Optional<Facility> facility = repo.getFacrepo().findById(id);
		if(facility.isPresent()) {
			List<Document> list = repo.getDocumentRepo().findByFacility(facility.get());
			repo.getDocumentRepo().deleteAll(list);
			Document fd = new Document();
			fd.setFacility(facility.get());
			fd.setDocumentPath(docPath);
			repo.getDocumentRepo().save(fd);
			}
			else
				throw new BusinessException(String.format("file was not uploaded"));	
	}
	
	@Transactional
	public void deleteFacility(String id) {
		Optional<Facility> facility = repo.getFacrepo().findById(id);
		if(facility.isPresent()) {
			repo.getFacrepo().delete(facility.get());
			}
			else
				throw new BusinessException(String.format("Facility was not deleted. Check if it is used in any projects or tasks then try again"));	
	}
}
