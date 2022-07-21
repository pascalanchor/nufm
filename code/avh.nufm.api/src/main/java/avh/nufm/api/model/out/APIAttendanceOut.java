package avh.nufm.api.model.out;

import java.util.List;

import avh.nufm.api.model.APIAttendance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIAttendanceOut extends APIAttendance{
private String eid;
private String workerName;
private String name;
private String phoneNumber;
private String email;
private List<String> specializations;

}
