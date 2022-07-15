package avh.nufm.api.model.in;

import avh.nufm.api.model.APITask;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APITaskIn extends APITask{
	
	private String WorkerName;
	private String comment;
}
