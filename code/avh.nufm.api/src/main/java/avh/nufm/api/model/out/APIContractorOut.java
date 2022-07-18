package avh.nufm.api.model.out;

import java.sql.Timestamp;

import avh.nufm.api.model.APIContractor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIContractorOut extends APIContractor{
	private String profileImage;
	private Timestamp createdAt;
}
