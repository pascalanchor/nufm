package avh.nufm.api.model.in;

import avh.nufm.api.model.ResetPassword;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@Getter 
@Setter
public class ForgetPasswordIn extends ResetPassword {
	private String newPassword;
}