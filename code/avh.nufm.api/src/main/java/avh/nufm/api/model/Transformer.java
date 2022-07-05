package avh.nufm.api.model;


import java.util.ArrayList;
import java.util.List;

import avh.nufm.api.model.in.FacilityIn;
import avh.nufm.api.model.in.FacilityTypeIn;
import avh.nufm.api.model.in.SpecializationIn;
import avh.nufm.api.model.out.FacilityOut;
import avh.nufm.api.model.out.FacilityTypeOut;
import avh.nufm.api.model.out.SpecializationOut;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.FacilityType;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.Specialization;

public class Transformer {

	public static FacilityType FacTypeToModel(FacilityTypeIn fctin){
		FacilityType res=new FacilityType();
		res.setName(fctin.getName());
		return res;
	}
	
	public static FacilityTypeOut FacTypeFromModel(FacilityType fact) {
		FacilityTypeOut res=new FacilityTypeOut();
		res.setEid(fact.getEid());
		res.setName(fact.getName());
		return res;
	}
	
	
	public static FacilityOut FacilityFromModel(Facility f) {
		FacilityOut res = new FacilityOut();
		
				
		res.setName(f.getName());		
		res.setOccupant_id(f.getNufmUser().getEid());
		res.setCreatedAt(f.getCreationDate());
		res.setLocation(f.getLocation());
		res.setType_id(f.getFacilityType().getEid());
		
		return res;
	}
	
	public static Facility FacilityToModel(FacilityIn fin) {
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
	
	public static Specialization specToModel(SpecializationIn specIn) {
		Specialization res=new Specialization();
		res.setName(specIn.getName());
		return res;
	}
	
	public static List<FacilityTypeOut> listFTypeFromIterable(Iterable<FacilityType> ftypIt){
		List<FacilityTypeOut> fctypeList=new ArrayList<>();
		for(FacilityType itr:ftypIt)
		{
			FacilityTypeOut fcto=FacTypeFromModel(itr);
			fctypeList.add(fcto);
		}
		
		return fctypeList;
		
	}
	
	public static SpecializationOut specFromModel(Specialization spec){
		SpecializationOut res=new SpecializationOut();
		res.setName(spec.getName());
		res.setEid(spec.getEid());
		
		return res;
	}
	
	
	
}
