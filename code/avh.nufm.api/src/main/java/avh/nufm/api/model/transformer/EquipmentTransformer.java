package avh.nufm.api.model.transformer;

import avh.nufm.api.model.in.APIEquipmentIn;
import avh.nufm.api.model.out.APIEquipmentOut;
import avh.nufm.business.model.Equipment;

public class EquipmentTransformer {
	public static Equipment EquipmentToModel(APIEquipmentIn bin) {
		Equipment res = new Equipment();
		
		return res;
	}

	public static APIEquipmentOut EquipmentFromModel(Equipment equipment) {
		APIEquipmentOut res = new APIEquipmentOut();
		
		return res;
	}
}
