package avh.nufm.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Contract;
import avh.nufm.business.model.ContractType;
import avh.nufm.business.model.Document;
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
		Optional<Contract> res = repo.getContractRepo().findByEid(contractId);
		repo.getContractRepo().delete(res.get());
	}

	@Transactional
	public Contract getContractById(String vendorId) {
		Optional<Contract> res = repo.getContractRepo().findByEid(vendorId);
		if (res.isPresent()) {
			return res.get();
		} else
			throw new BusinessException(String.format("contract does not exist"));
	}
	
	@Transactional
	public Contract addContract(Contract contract, String typeId) {
		contract.setEid(UUID.randomUUID().toString());
		Optional<ContractType> contractType = repo.getContractTypeRepo().findByEid(typeId);
		if (contractType.isPresent()) {
			contract.setTypeId(contractType.get().getEid());
		} else {
			throw new BusinessException(String.format("contract type does not exist"));
		}
		repo.getContractRepo().save(contract);
		return contract;
	}

	public void addContractDocs(String eid, List<String> docsPaths) {
		Optional<Contract> contract = repo.getContractRepo().findByEid(eid);
		if(contract.isPresent()) {
			List<Document> list = repo.getDocumentRepo().findByContractId(contract.get().getEid());
			repo.getDocumentRepo().deleteAll(list);
			for (String docPath : docsPaths) {
				Document fd = new Document();
				fd.setContractId(contract.get().getEid());
				fd.setDocumentPath(docPath);
				repo.getDocumentRepo().save(fd);
			}
		} else {
			throw new BusinessException(String.format("files were not uploaded"));	
		}
	}

	@Transactional
	public Contract updateContract(String id, Contract contractModel, String typeId) {
		Optional<Contract> oldContract = repo.getContractRepo().findByEid(id);
		if (oldContract.isEmpty()) {
			throw new BusinessException(String.format("contract does not exist"));
		}
		Contract res = oldContract.get();
		Optional<ContractType> contractType = repo.getContractTypeRepo().findByEid(typeId);
		if (contractType.isPresent()) {
			res.setTypeId(contractType.get().getEid());
		} else {
			throw new BusinessException(String.format("Contract type does not exist"));
		}
		res.setCreditPeriod(contractModel.getCreditPeriod());
		res.setEndDate(contractModel.getEndDate());
		res.setNoticeDate(contractModel.getNoticeDate());
		res.setNumber(contractModel.getNumber());
		res.setPaymentMethod(contractModel.getPaymentMethod());
		res.setReviewDate(contractModel.getReviewDate());
		res.setStartDate(contractModel.getStartDate());
		res.setSupplier(contractModel.getSupplier());
		res.setTemplate(contractModel.getTemplate());
		res.setTitle(contractModel.getTitle());
		repo.getContractRepo().save(res);
		return res;
	}
}
