package avh.nufm.api.model.out;

import avh.nufm.api.model.Specialization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpecializationOut extends Specialization{
	private String eid;
}
