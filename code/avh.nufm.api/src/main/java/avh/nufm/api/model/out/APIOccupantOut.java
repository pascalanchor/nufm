package avh.nufm.api.model.out;

import java.sql.Timestamp;

import avh.nufm.api.model.APIOccupant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
public class APIOccupantOut extends APIOccupant{
	private Timestamp createdAt;
}
