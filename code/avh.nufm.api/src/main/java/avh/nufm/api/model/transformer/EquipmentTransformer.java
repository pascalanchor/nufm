package avh.nufm.api.model.transformer;

import avh.nufm.api.model.in.APIEquipmentIn;
import avh.nufm.api.model.out.APIEquipmentOut;
import avh.nufm.business.model.Equipment;

public class EquipmentTransformer {
	public static Equipment EquipmentToModel(APIEquipmentIn ein) {
		Equipment res = new Equipment();
		res.setName(ein.getName());
		res.setDescription(ein.getDescription());
		res.setLocation(ein.getLocation());
		res.setNumberOfItems(ein.getNumberOfItems());
		res.setPurchaseDate(ein.getPurchaseDate());
		res.setUnitCost(ein.getUnitCost());
		res.setZipCode(ein.getZipCode());
		return res;
	}

	public static APIEquipmentOut EquipmentFromModel(Equipment equipment) {
		APIEquipmentOut res = new APIEquipmentOut();
		res.setEid(equipment.getEid());
		return res;
	}
}
