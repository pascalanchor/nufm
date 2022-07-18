package avh.nufm.api.model.transformer;


import avh.nufm.api.model.in.APIOccupantIn;
import avh.nufm.api.model.out.APIOccupantOut;
import avh.nufm.business.model.NufmUser;


public class OccupantTransformer {
	public static NufmUser ModelFromOccupant(APIOccupantIn occupantIn) {
		NufmUser res = new NufmUser();
		res.setEid(occupantIn.getEmail());
		res.setFullName(occupantIn.getFullName());
		res.setPhone(occupantIn.getPhone());
		return res;
	}
	
	public static APIOccupantOut OccupantFromModel(NufmUser occupant) {
		APIOccupantOut res = new APIOccupantOut();
		res.setEmail(occupant.getEid());
		res.setFullName(occupant.getFullName());
		res.setProfileImage(occupant.getProfileImage());
		res.setPhone(occupant.getPhone());
		res.setCreatedAt(occupant.getCreatedAt());
		return res;
	}
}
