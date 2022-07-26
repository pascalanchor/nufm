package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;

import avh.nufm.api.model.in.APITaskIn;
import avh.nufm.api.model.out.APITaskOut;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.Project;
import avh.nufm.business.model.Task;
import avh.nufm.business.model.TaskType;

public class TaskTransformer {

	
	public static APITaskOut taskFromModel(Task tsk) {
		APITaskOut res=new APITaskOut();
		//res.setComment(tsk.getComment());
		//res.setDate_from(tsk.getDateFrom());
		//res.setDate_to(tsk.getDateTo());
		//res.setDocumentId(tsk.getDocumentId());
		res.setEid(tsk.getEid());
		res.setFacilityName(tsk.getFacility().getName());
		res.setName(tsk.getName());
		res.setProjectName(tsk.getProject().getName());
		res.setStatus(tsk.getStatus());
		res.setTaskType(tsk.getTaskType().getName());
		res.setCreationDate(tsk.getCreatedAt());
		
		//res.setType_id(tsk.getTaskType().getEid());
		
		return res;
		
	}
	
	public static Task taskToModel(APITaskIn tskin) {
		Task res=new Task();
		
		//create facility instance
		Facility fc=new Facility();
		fc.setName(tskin.getFacilityName());
		res.setFacility(fc);
		res.setName(tskin.getName());
		//create project instance
		Project prj=new Project();
		prj.setName(tskin.getProjectName());
		res.setProject(prj);
		res.setStatus(tskin.getStatus());
		TaskType tsktype=new TaskType();
		tsktype.setName(tskin.getTaskType());
		res.setTaskType(tsktype);
		res.setCreatedAt(tskin.getCreationDate());
		res.setComment(tskin.getComment());
		
		return res;
	}
	
	
	public static List<APITaskOut> listTaskFromItr(Iterable<Task> tskitr){
		List<APITaskOut> res=new ArrayList<APITaskOut>();
		for(Task itr:tskitr) {
			res.add(taskFromModel(itr));
		}
		return res;
	}
}
