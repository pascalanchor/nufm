package avh.nufm.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Document;
import avh.nufm.business.model.Vendor;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class VendorControllerImpl {
	@Autowired
	private NufmRepos repo;
	
	@Transactional
	public List<Vendor> getVendors() {
		List<Vendor> vendors = repo.getVendorRepo().findAll();
		return vendors;
	}

	@Transactional
	public void deleteVendor(String vendorId) {
		Optional<Vendor> res = repo.getVendorRepo().findById(vendorId);
		repo.getVendorRepo().delete(res.get());
	}

	@Transactional
	public Vendor getVendorById(String vendorId) {
		Optional<Vendor> res = repo.getVendorRepo().findById(vendorId);
		if (res.isPresent()) {
			return res.get();
		} else
			throw new BusinessException(String.format("vendor does not exist"));
	}

	@Transactional
	public Vendor addVendor(Vendor vendor) {
		vendor.setEid(UUID.randomUUID().toString());
		repo.getVendorRepo().save(vendor);
		return vendor;
	}

	public void addVendorDocs(String eid, List<String> storeMultipleFile) {
		Optional<Vendor> vendor = repo.getVendorRepo().findById(eid);
		if(vendor.isPresent()) {
			List<Document> list = repo.getDocumentRepo().findByVendor(vendor.get());
			repo.getDocumentRepo().deleteAll(list);
		} else {
			throw new BusinessException(String.format("file was not uploaded"));	
		}
	}
}
