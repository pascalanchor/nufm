package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;

import avh.nufm.api.model.in.APIContractorIn;
import avh.nufm.api.model.out.APIContractorOut;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserSpecialization;

public class ContractorTransformer {
	public static NufmUser ModelFromContractor(APIContractorIn contractorIn) {
		NufmUser res = new NufmUser();
		res.setEid(contractorIn.getEmail());
		res.setFullName(contractorIn.getFullName());
		res.setNationalId(contractorIn.getNationalIdentity());
		res.setPhone(contractorIn.getPhone());
		return res;
	}
	
	public static APIContractorOut ContractorFromModel(NufmUser contractor) {
		APIContractorOut res = new APIContractorOut();
		res.setEmail(contractor.getEid());
		res.setFullName(contractor.getFullName());
		res.setNationalIdentity(contractor.getNationalId());
		res.setPhone(contractor.getPhone());
		List<String> specList = new ArrayList<>();
		for(UserSpecialization us: contractor.getUserSpecializations()) {
			specList.add(us.getSpecialization().getName());
		}
		res.setSpecializations(specList);
		res.setCreatedAt(contractor.getCreatedAt());
		return res;
	}
}
