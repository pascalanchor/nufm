package avh.nufm.api.model.transformer;

import avh.nufm.api.model.in.APIFacilityIn;
import avh.nufm.api.model.out.APIFacilityOut;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.FacilityType;
import avh.nufm.business.model.NufmUser;

public class FacilityTransformer {

	public static Facility FacilityToModel(APIFacilityIn fin) {
		Facility res = new Facility();
		res.setName(fin.getName());
		res.setLocation(fin.getLocation());
		// temporary instance to transport the occupant id to the controller
		// implementation
		NufmUser occupant = new NufmUser();
		occupant.setEid(fin.getOccupantId());
		res.setNufmUser(occupant);
		// temporary instance to transport the facility_type id to the controller
		// implementation
		FacilityType type = new FacilityType();
		type.setEid(fin.getType());
		res.setFacilityType(type);
		return res;
	}

	public static APIFacilityOut FacilityFromModel(Facility facility) {
		APIFacilityOut res = new APIFacilityOut();
		res.setEid(facility.getEid());
		if (facility.getFacility() != null) {
			res.setParentId(facility.getFacility().getName());
		} else
			res.setParentId("No parent");
		res.setName(facility.getName());
		res.setType(facility.getFacilityType().getName());
		res.setLocation(facility.getLocation());
		res.setOccupantName(facility.getNufmUser().getFullName());
		res.setCreatedAt(facility.getCreationDate());
		return res;
	}
}
