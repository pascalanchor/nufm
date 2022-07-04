package avh.nufm.api.model.transformer;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import avh.nufm.api.model.in.APIUserIn;
import avh.nufm.api.model.out.APIUserOut;
import avh.nufm.business.model.NufmUser;

public class UserTransformer {
	
	public static NufmUser ModelFromUser(APIUserIn u) {
		NufmUser res = new NufmUser();
		res.setFullName(u.getFullName());
		res.setNationalId(u.getNationalIdentity());
		res.setPhone(u.getPhone());
		return res;
	}
	
	public static APIUserOut UserFromModel(NufmUser nu) {
		APIUserOut res=new APIUserOut();
		res.setEmail(nu.getEid());
		res.setFullName(nu.getFullName());
		res.setNationalIdentity(nu.getNationalId());
		res.setPhone(nu.getPhone());
		res.setCreationDate(nu.getCreatedAt());
		return res;
	}
	
	public static NufmUser ModelUpdate(NufmUser user, APIUserIn in) {
		NufmUser res = ModelFromUser(in);
		res.setCreatedAt(user.getCreatedAt());
		res.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
		return res;
	}
}
