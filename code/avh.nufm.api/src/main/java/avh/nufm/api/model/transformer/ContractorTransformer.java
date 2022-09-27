package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import avh.nufm.api.model.in.APIContractorIn;
import avh.nufm.api.model.out.APIContractorOut;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserSpecialization;
import avh.nufm.business.model.repository.NufmRepos;

public class ContractorTransformer {
	@Autowired private static NufmRepos repo;
	public static NufmUser ModelFromContractor(APIContractorIn contractorIn) {
		NufmUser res = new NufmUser();
		res.setEid(contractorIn.getEmail());
		res.setFullName(contractorIn.getFullName());
		res.setPhone(contractorIn.getPhone());
		return res;
	}
	
	public static APIContractorOut ContractorFromModel(NufmUser contractor) {
		APIContractorOut res = new APIContractorOut();
		res.setEmail(contractor.getEid());
		res.setFullName(contractor.getFullName());
		res.setProfileImage(contractor.getProfileImage());
		res.setPhone(contractor.getPhone());
		List<String> specList = new ArrayList<>();
		List<UserSpecialization> specs = repo.getUserSpecrepo().findByNufmUser(contractor);
		for(UserSpecialization us: specs) {
			specList.add(us.getSpecialization().getName());
		}
		res.setSpecializations(specList);
		res.setCreatedAt(contractor.getCreatedAt());
		return res;
	}
}
