package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;
import avh.nufm.api.model.out.APIAttendanceOut;
import avh.nufm.business.model.Attendance;


public class AttendanceTransformer {

	public static APIAttendanceOut AttandanceFromModel(Attendance atd) {
		APIAttendanceOut res=new APIAttendanceOut();
		res.setEid(atd.getEid());
		res.setStatus(atd.getStatus());
		res.setWorkerName(atd.getNufmUser().getFullName());
		
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
