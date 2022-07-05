package avh.nufm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.UserControllerImpl;
import avh.nufm.api.model.APIUser;
import avh.nufm.api.model.Transformer;
import avh.nufm.api.model.in.APIUserIn;
import avh.nufm.api.model.out.APIUserOut;
import avh.nufm.api.model.transformer.UserTransformer;
import avh.nufm.business.model.NufmUser;

@RestController
public class UserController {
@Autowired private UserControllerImpl usi;

@PostMapping("avh/nufm/v1/private/user/add")
public ResponseEntity<APIUserOut> addUser(@RequestBody APIUserIn usin)
{
	try {
		NufmUser user=UserTransformer.ModelFromUser(usin);
		APIUserOut res=UserTransformer.UserFromModel(usi.createUser(user));
		return ResponseEntity.ok().body(res);
	}catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}




}
