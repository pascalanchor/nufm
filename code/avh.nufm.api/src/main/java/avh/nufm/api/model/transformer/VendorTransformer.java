package avh.nufm.api.model.transformer;

import avh.nufm.api.model.in.APIVendorIn;
import avh.nufm.api.model.out.APIVendorOut;
import avh.nufm.business.model.Vendor;

public class VendorTransformer {
	public static Vendor VendorToModel(APIVendorIn vin) {
		Vendor res = new Vendor();
		res.setCompanyName(vin.getCompanyName());
		res.setContactName(vin.getContactName());
		res.setEmail(vin.getEmail());
		res.setWebsite(vin.getWebsite());
		res.setPhoneNumber(vin.getPhoneNumber());
		res.setCity(vin.getCity());
		res.setStreet(vin.getStreet());
		res.setZipCode(vin.getZipCode());
		res.setLocation(vin.getLocation());
		return res;
	}

	public static APIVendorOut VendorFromModel(Vendor vendor) {
		APIVendorOut res = new APIVendorOut();
		res.setEid(vendor.getEid());
		return res;
	}
}
