package avh.nufm.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Document;
import avh.nufm.business.model.Equipment;
import avh.nufm.business.model.EquipmentType;
import avh.nufm.business.model.Vendor;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class EquipmentControllerImpl {
	@Autowired
	private NufmRepos repo;
	
	@Transactional
	public List<Equipment> getEquipments() {
		List<Equipment> equipments = repo.getEqmtrepo().findAll();
		return equipments;
	}

	@Transactional
	public void deleteEquipment(String equipmentId) {
		Optional<Equipment> res = repo.getEqmtrepo().findById(equipmentId);
		repo.getEqmtrepo().delete(res.get());
	}

	@Transactional
	public Equipment getEquipmentById(String equipmentId) {
		Optional<Equipment> res = repo.getEqmtrepo().findById(equipmentId);
		if (res.isPresent()) {
			return res.get();
		} else
			throw new BusinessException(String.format("equipment does not exist"));
	}
	
	@Transactional
	public Equipment addEquipment(Equipment equipment, String typeId, String vendorId) {
		equipment.setEid(UUID.randomUUID().toString());
		Optional<EquipmentType> equipmentType = repo.getEquipmentTypeRepo().findByEid(typeId);
		if (equipmentType.isPresent()) {
			equipment.setTypeId(equipmentType.get().getEid());
		} else {
			throw new BusinessException(String.format("equipment type does not exist"));
		}
		Optional<Vendor> vendor = repo.getVendorRepo().findByEid(vendorId);
		if (vendor.isPresent()) {
			equipment.setVendorId(vendor.get().getEid());
		} else {
			throw new BusinessException(String.format("vendor does not exist"));
		}
		repo.getEqmtrepo().save(equipment);
		return equipment;
	}

	public void addEquipmentDocs(String eid, List<String> docsPaths) {
		Optional<Equipment> equipment = repo.getEqmtrepo().findByEid(eid);
		if(equipment.isPresent()) {
			List<Document> list = repo.getDocumentRepo().findByEquipmentId(equipment.get().getEid());
			repo.getDocumentRepo().deleteAll(list);
			for (String docPath : docsPaths) {
				Document fd = new Document();
				fd.setEquipmentId(equipment.get().getEid());
				fd.setDocumentPath(docPath);
				repo.getDocumentRepo().save(fd);
			}
		} else {
			throw new BusinessException(String.format("files were not uploaded"));	
		}
	}

	@Transactional
	public Equipment updateEquipment(String id, Equipment equipment, String typeId, String vendorId) {
		Optional<Equipment> oldEquipment = repo.getEqmtrepo().findByEid(id);
		if (oldEquipment.isEmpty()) {
			throw new BusinessException(String.format("equipment does not exist"));
		}
		Equipment res = oldEquipment.get();
		Optional<EquipmentType> equipmentType = repo.getEquipmentTypeRepo().findByEid(typeId);
		if (equipmentType.isPresent()) {
			res.setTypeId(equipmentType.get().getEid());
		} else {
			throw new BusinessException(String.format("equipment type does not exist"));
		}
		Optional<Vendor> vendor = repo.getVendorRepo().findByEid(vendorId);
		if (vendor.isPresent()) {
			res.setVendorId(vendor.get().getEid());
		} else {
			throw new BusinessException(String.format("vendor does not exist"));
		}
		res.setName(equipment.getName());
		res.setDescription(equipment.getDescription());
		res.setLocation(equipment.getLocation());
		res.setNumberOfItems(equipment.getNumberOfItems());
		res.setPurchaseDate(equipment.getPurchaseDate());
		res.setUnitCost(equipment.getUnitCost());
		res.setZipCode(equipment.getZipCode());
		repo.getEqmtrepo().save(res);
		return res;
	}
}
