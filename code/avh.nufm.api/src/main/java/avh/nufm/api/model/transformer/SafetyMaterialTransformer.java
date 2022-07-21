package avh.nufm.api.model.transformer;

import java.util.ArrayList;
import java.util.List;

import avh.nufm.api.model.in.APISafetyMaterialIn;
import avh.nufm.api.model.out.APISafetyMaterialOut;
import avh.nufm.business.model.SafetyMaterial;

public class SafetyMaterialTransformer {

	
	public static SafetyMaterial sftToModel(APISafetyMaterialIn sftin) {
		SafetyMaterial res=new SafetyMaterial();
		res.setName(sftin.getName());
		res.setStatus(sftin.getStatus());
		res.setType(sftin.getType());
		return res;
	}
	
	public static APISafetyMaterialOut sftFromModel(SafetyMaterial sft) {
		APISafetyMaterialOut res=new APISafetyMaterialOut();
		res.setEid(sft.getEid());
		res.setName(sft.getName());
		res.setStatus(sft.getStatus());
		res.setType(sft.getType());
		return res;
	}
	
	public static List<APISafetyMaterialOut> listFromItr(Iterable<SafetyMaterial> sftItr){
		List<APISafetyMaterialOut> res=new ArrayList<APISafetyMaterialOut>();
		
		for(SafetyMaterial itr:sftItr) {
			res.add(sftFromModel(itr));
		}
		
		return res;
	}
}
