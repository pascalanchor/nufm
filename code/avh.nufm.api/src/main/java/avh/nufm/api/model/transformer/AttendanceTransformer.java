package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import avh.nufm.api.model.out.APIAttendanceOut;
import avh.nufm.business.model.Attendance;
import avh.nufm.business.model.UserSpecialization;
import avh.nufm.business.model.repository.NufmRepos;


public class AttendanceTransformer {
	@Autowired private static NufmRepos repo;
	public static APIAttendanceOut AttandanceFromModel(Attendance atd) {
		APIAttendanceOut res=new APIAttendanceOut();
		res.setEid(atd.getEid());
		res.setStatus(atd.getStatus());
		res.setWorkerName(atd.getNufmUser().getFullName());
		res.setPhoneNumber(atd.getNufmUser().getPhone());
		res.setName(atd.getNufmUser().getFullName());
		res.setEmail(atd.getEid());
		List<String> specList=new ArrayList<String>();
		List<UserSpecialization> specs = repo.getUserSpecrepo().findByNufmUser(atd.getNufmUser());
		for(UserSpecialization spec:specs) {
			specList.add(spec.getSpecialization().getName());
		}
		res.setSpecializations(specList);
		return res;
	}
	
	public static List<APIAttendanceOut> listAttendanceFromIterable(Iterable<Attendance> attItr){
		List<APIAttendanceOut> res=new ArrayList<>();
		for(Attendance itr:attItr) {
			res.add(AttandanceFromModel(itr));
		}
		return res;
	}
}
