package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;

import avh.nufm.api.model.in.APIWorkerIn;
import avh.nufm.api.model.out.APIWorkerOut;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.UserSpecialization;

public class WorkerTransformer {
	public static NufmUser ModelFromWorker(APIWorkerIn workerIn) {
		NufmUser res = new NufmUser();
		res.setEid(workerIn.getEmail());
		res.setFullName(workerIn.getFullName());
		res.setNationalId(workerIn.getNationalIdentity());
		res.setPhone(workerIn.getPhone());
		return res;
	}
	
	public static APIWorkerOut WorkerFromModel(NufmUser worker) {
		APIWorkerOut res = new APIWorkerOut();
		res.setEmail(worker.getEid());
		res.setFullName(worker.getFullName());
		res.setNationalIdentity(worker.getNationalId());
		res.setPhone(worker.getPhone());
		List<String> specList = new ArrayList<>();
		for(UserSpecialization us: worker.getUserSpecializations()) {
			specList.add(us.getSpecialization().getName());
		}
		res.setSpecializations(specList);
		res.setCreatedAt(worker.getCreatedAt());
		return res;
	}
}
