package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;

import avh.nufm.api.model.in.APIProjectIn;
import avh.nufm.api.model.out.APIProjectOut;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.Project;

public class ProjectTransformer {

	public static APIProjectOut projectFromModel(Project prj) {
		APIProjectOut res=new APIProjectOut();
		res.setComment(prj.getComment());
		res.setContractor_id(prj.getNufmUser().getEid());
		res.setDocument_id(prj.getDocumentId());
		res.setEid(prj.getEid());
		res.setFacility_id(prj.getFacility().getEid());
		res.setName(prj.getName());
		res.setStatus(prj.getStatus());
		res.setFromDate(prj.getDateFrom());
		res.setToDate(prj.getDateTo());
		return res;
		
	}
	
	
	public static Project projectToModel(APIProjectIn prjIn) {
		Project res=new Project();
		res.setComment(prjIn.getComment());
		res.setDocumentId(prjIn.getDocument_id());
		Facility fc=new Facility();
		fc.setEid(prjIn.getFacility_id());
		NufmUser usr = new NufmUser();
		usr.setEid(prjIn.getContractor_id());
		res.setNufmUser(usr);
		res.setFacility(fc);
		res.setName(prjIn.getName());
		res.setStatus(prjIn.getStatus());
		res.setDateFrom(prjIn.getFromDate());
		res.setDateTo(prjIn.getToDate());
		return res;
	}
	 
	public static List<APIProjectOut> prjListFromIterable(Iterable<Project> pritr){
		List<APIProjectOut> res=new ArrayList<APIProjectOut>();
		for (Project itr :pritr) {
			res.add(projectFromModel(itr));
		}
		
		return res;
	}
}
