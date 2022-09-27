package avh.nufm.api.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Equipment;
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
			throw new BusinessException(String.format("contract does not exist"));
	}
}
