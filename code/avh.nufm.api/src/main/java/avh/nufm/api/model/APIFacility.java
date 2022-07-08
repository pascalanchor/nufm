package avh.nufm.api.model;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class APIFacility {	
	private String parentId;
	private String name;
	private String type;
	private String location;
}
