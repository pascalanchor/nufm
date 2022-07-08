package avh.nufm.api.model.out;


import avh.nufm.api.model.APITaskType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APITaskTypeOut extends APITaskType{
private String eid;
}
