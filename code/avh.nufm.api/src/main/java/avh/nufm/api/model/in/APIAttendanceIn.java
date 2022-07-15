package avh.nufm.api.model.in;

import avh.nufm.api.model.APIAttendance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIAttendanceIn extends APIAttendance{
private String eid;
private String workerId;
}
