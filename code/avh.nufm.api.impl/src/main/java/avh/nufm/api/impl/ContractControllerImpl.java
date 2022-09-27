package avh.nufm.api.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Contract;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class ContractControllerImpl {
	@Autowired
	private NufmRepos repo;
	
	@Transactional
	public List<Contract> getContracts() {
		List<Contract> contracts = repo.getContractRepo().findAll();
		return contracts;
	}

	@Transactional
	public void deleteContract(String contractId) {
		Optional<Contract> res = repo.getContractRepo().findById(contractId);
		repo.getContractRepo().delete(res.get());
	}

	@Transactional
	public Contract getContractById(String vendorId) {
		Optional<Contract> res = repo.getContractRepo().findById(vendorId);
		if (res.isPresent()) {
			return res.get();
		} else
			throw new BusinessException(String.format("contract does not exist"));
	}
}
