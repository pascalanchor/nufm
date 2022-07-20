package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;
import avh.nufm.api.model.out.APIAttendanceOut;
import avh.nufm.business.model.Attendance;
import avh.nufm.business.model.UserSpecialization;


public class AttendanceTransformer {

	public static APIAttendanceOut AttandanceFromModel(Attendance atd) {
		APIAttendanceOut res=new APIAttendanceOut();
		res.setEid(atd.getEid());
		res.setStatus(atd.getStatus());
		res.setWorkerName(atd.getNufmUser().getFullName());
		res.setPhoneNumber(atd.getNufmUser().getPhone());
		res.setName(atd.getNufmUser().getFullName());
		res.setEmail(atd.getEid());
		List<String> specList=new ArrayList<String>();
		for(UserSpecialization spec:atd.getNufmUser().getUserSpecializations()) {
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
