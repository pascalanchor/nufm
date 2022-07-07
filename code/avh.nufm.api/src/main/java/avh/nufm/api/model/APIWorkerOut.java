package avh.nufm.api.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIWorkerOut extends APIWorker{
	private Timestamp createdAt;
}
