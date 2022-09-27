package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import avh.nufm.api.model.in.APIWorkerIn;
import avh.nufm.api.model.out.APIWorkerOut;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserSpecialization;
import avh.nufm.business.model.repository.NufmRepos;

public class WorkerTransformer {
	@Autowired private static NufmRepos repo;

	
	public static NufmUser ModelFromWorker(APIWorkerIn workerIn) {
		NufmUser res = new NufmUser();
		res.setEid(workerIn.getEmail());
		res.setFullName(workerIn.getFullName());
		res.setPhone(workerIn.getPhone());
		return res;
	}
	
	public static APIWorkerOut WorkerFromModel(NufmUser worker) {
		APIWorkerOut res = new APIWorkerOut();
		res.setEmail(worker.getEid());
		res.setFullName(worker.getFullName());
		res.setProfileImage(worker.getProfileImage());
		res.setPhone(worker.getPhone());
		List<String> specList = new ArrayList<>();
		List<UserSpecialization> specs = repo.getUserSpecrepo().findByNufmUser(worker);
		for(UserSpecialization us: specs) {
			specList.add(us.getSpecialization().getName());
		}
		res.setSpecializations(specList);
		res.setCreatedAt(worker.getCreatedAt());
		return res;
	}
}
