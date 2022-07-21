package avh.nufm.api.model;



import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APITask {
private String facilityName;
private String taskType;
private String projectName;
private String name;
private String status;
private String creationDate;
private List<String> workersName;

}
