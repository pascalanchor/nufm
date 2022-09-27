package avh.nufm.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIEquipment {
	private String description;
	private String location;
	private String name;
	private Integer numberOfItems;
	private String purchaseDate;
	private String typeId;
	private double unitCost;
	private String vendorId;
	private String zipCode;
}
