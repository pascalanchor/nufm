package avh.nufm.api.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APISafetyMaterial {
private String name;
private String status;
private String type;
private List<String> workers;
private String materialImage;
}
