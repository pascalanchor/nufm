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
		Optional<Vendor> res = repo.getVendorRepo().findByEid(vendorId);
		repo.getVendorRepo().delete(res.get());
	}

	@Transactional
	public Vendor getVendorById(String vendorId) {
		Optional<Vendor> res = repo.getVendorRepo().findByEid(vendorId);
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

	public void addVendorDocs(String eid, List<String> docsPaths) {
		Optional<Vendor> vendor = repo.getVendorRepo().findByEid(eid);
		if(vendor.isPresent()) {
			List<Document> list = repo.getDocumentRepo().findByVendorId(vendor.get().getEid());
			repo.getDocumentRepo().deleteAll(list);
			for (String docPath : docsPaths) {
				Document fd = new Document();
				fd.setVendorId(vendor.get().getEid());
				fd.setDocumentPath(docPath);
				repo.getDocumentRepo().save(fd);
			}
		} else {
			throw new BusinessException(String.format("files were not uploaded"));	
		}
	}
	
	@Transactional
	public Vendor updateVendor(String id, Vendor vendorModel) {
		Optional<Vendor> oldVendor = repo.getVendorRepo().findByEid(id);
		if (oldVendor.isEmpty()) {
			throw new BusinessException(String.format("vendor does not exist"));
		}
		Vendor res = oldVendor.get();
		res.setCompanyName(vendorModel.getCompanyName());
		res.setContactName(vendorModel.getContactName());
		res.setEmail(vendorModel.getEmail());
		res.setWebsite(vendorModel.getWebsite());
		res.setPhoneNumber(vendorModel.getPhoneNumber());
		res.setCity(vendorModel.getCity());
		res.setStreet(vendorModel.getStreet());
		res.setZipCode(vendorModel.getZipCode());
		res.setLocation(vendorModel.getLocation());
		repo.getVendorRepo().save(res);
		return res;
	}
}
