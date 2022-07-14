package avh.nufm.api.model.out;

import java.sql.Timestamp;

import avh.nufm.api.model.APIWorker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIWorkerOut extends APIWorker{
	private String profileImage;
	private Timestamp createdAt;
}
