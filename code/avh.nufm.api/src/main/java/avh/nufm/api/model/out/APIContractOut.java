package avh.nufm.api.model.out;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIContractOut {
	private String eid;
	private Timestamp createdAt;
}
