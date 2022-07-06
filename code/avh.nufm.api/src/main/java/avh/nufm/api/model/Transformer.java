package avh.nufm.api.model;


import java.util.ArrayList;
import java.util.List;

import avh.nufm.api.model.in.APIFacilityIn;
import avh.nufm.api.model.in.APIFacilityTypeIn;
import avh.nufm.api.model.in.APIProjectIn;
import avh.nufm.api.model.in.APISpecializationIn;
import avh.nufm.api.model.in.APITaskTypeIn;
import avh.nufm.api.model.out.APIProjectOut;
import avh.nufm.api.model.out.APIFacilityOut;
import avh.nufm.api.model.out.APIFacilityTypeOut;
import avh.nufm.api.model.out.APISpecializationOut;
import avh.nufm.api.model.out.APITaskTypeOut;
import avh.nufm.business.model.Contractor;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.FacilityType;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.Project;
import avh.nufm.business.model.Specialization;
import avh.nufm.business.model.TaskType;

public class Transformer {

	public static FacilityType FacTypeToModel(APIFacilityTypeIn fctin){
		FacilityType res=new FacilityType();
		res.setName(fctin.getName());
		return res;
	}
	
	public static APIFacilityTypeOut FacTypeFromModel(FacilityType fact) {
		APIFacilityTypeOut res=new APIFacilityTypeOut();
		res.setEid(fact.getEid());
		res.setName(fact.getName());
		return res;
	}
	
	
	public static APIFacilityOut FacilityFromModel(Facility f) {
		APIFacilityOut res = new APIFacilityOut();
		
				
		res.setName(f.getName());		
		res.setOccupant_id(f.getNufmUser().getEid());
		res.setCreatedAt(f.getCreationDate());
		res.setLocation(f.getLocation());
		res.setType_id(f.getFacilityType().getEid());
		
		return res;
	}
	
	public static Facility FacilityToModel(APIFacilityIn fin) {
		Facility res = new Facility();
		
		res.setName(fin.getName());
		//create occupant with only id
		NufmUser occupant=new NufmUser();
		occupant.setEid(fin.getOccupant_id());
		res.setNufmUser(occupant);
		//create facilityType with only id
		FacilityType type=new FacilityType();
		type.setEid(fin.getType_id());
		res.setLocation(fin.getLocation());
		//create the facility parent 
		Facility parent=new Facility();
		parent.setEid(fin.getParent_id());
		res.setFacility(parent);
		
		
		return res;
	}
	
	//specialization transformers
	
	public static Specialization specToModel(APISpecializationIn specIn) {
		Specialization res=new Specialization();
		res.setName(specIn.getName());
		return res;
	}
	
	public static List<APIFacilityTypeOut> listFTypeFromIterable(Iterable<FacilityType> ftypIt){
		List<APIFacilityTypeOut> fctypeList=new ArrayList<>();
		for(FacilityType itr:ftypIt)
		{
			APIFacilityTypeOut fcto=FacTypeFromModel(itr);
			fctypeList.add(fcto);
		}
		
		return fctypeList;
		
	}
	
	public static APISpecializationOut specFromModel(Specialization spec){
		APISpecializationOut res=new APISpecializationOut();
		res.setName(spec.getName());
		res.setEid(spec.getEid());
		
		return res;
	}
	
	//---------------------project transformer ---------------------
	public static APIProjectOut ProjectFromModel(Project prj) {
		APIProjectOut res=new APIProjectOut();
		res.setEid(prj.getEid());
		res.setFacility_id(prj.getFacility().getEid());
		res.setComment(prj.getComment());
		res.setContractor_id(prj.getContractor().getEid());
		res.setDateFrom(prj.getDateFrom());
		res.setDateTo(prj.getDateTo());
		res.setName(prj.getName());
		res.setDocument_id(prj.getDocumentId());
	
		return res;
	}
	
	
	public static Project ProjectToModel(APIProjectIn prjin) {
		Project res=new Project();
		res.setComment(prjin.getComment());
		res.setDateFrom(prjin.getDateFrom());
		res.setDateTo(prjin.getDateTo());
		//create facility has only id 
		Facility fc=new Facility();
		fc.setEid(prjin.getFacility_id());
		res.setFacility(fc);
		//create contractor has only id
		Contractor contractor=new Contractor();
		contractor.setEid(prjin.getContractor_id());
		res.setContractor(contractor);
		res.setName(prjin.getName());
		res.setStatus(prjin.getStatus());
		res.setDocumentId(prjin.getDocument_id());
		
		
		return res;
	}
	
	//transform from iterable to list
	public static List<APIProjectOut> prjListFromIterable(Iterable<Project> prjitr){
		List<APIProjectOut> res=new ArrayList<APIProjectOut>();
		for(Project itr:prjitr) {
			res.add(ProjectFromModel(itr));
		}
		
		return res;
	}
	
	//---------------------projet transformer ---------------------
	
	
	//------------task type --------------------
	
	public static TaskType TaskTypeToModel(APITaskTypeIn tstIn) {
		TaskType res=new TaskType();
		res.setName(tstIn.getName());
		 
		return res;
	}
	
	public static APITaskTypeOut TaskTypeFromModel(TaskType tst) {
		APITaskTypeOut res=new APITaskTypeOut();
		res.setEid(tst.getEid());
		res.setName(tst.getName());
		return res;
		
	}
	
	public static List<APITaskTypeOut> ListTstFromIterable(Iterable<TaskType> tstItr){
		List<APITaskTypeOut> tstList=new ArrayList<>();
		for(TaskType itr: tstItr) {
			tstList.add(Transformer.TaskTypeFromModel(itr));
		}
		
		return tstList;
	}
	
	//------------task type --------------------
}
