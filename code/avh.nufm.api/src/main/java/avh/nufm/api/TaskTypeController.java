//package avh.nufm.api;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//
//import avh.nufm.api.impl.TaskTypeControllerImpl;
//import avh.nufm.api.model.Transformer;
//import avh.nufm.api.model.in.APITaskTypeIn;
//import avh.nufm.api.model.out.APITaskTypeOut;
//import avh.nufm.business.model.TaskType;
//
//@RestController
//public class TaskTypeController {
//
//	@Autowired private TaskTypeControllerImpl tstimp;
//	
//	
//	@PostMapping("avh/nufm/v1/private/TaskType/add")
//	public ResponseEntity<APITaskTypeOut> addTaskType(APITaskTypeIn tstIn){
//		try
//		{
//			TaskType tst=Transformer.TaskTypeToModel(tstIn);
//			APITaskTypeOut res=Transformer.TaskTypeFromModel(tstimp.addTaskType(tst));
//			return ResponseEntity.ok().body(res);
//		}catch(Exception e) {
//			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
//		}
//		
//	}
//	
//	
//	@GetMapping("avh/nufm/v1/private/TaskTypes")
//	public ResponseEntity<List<APITaskTypeOut>> getAlltaskType(){
//		List<APITaskTypeOut> res=Transformer.ListTstFromIterable(tstimp.getAllTaskType());
//		return ResponseEntity.ok().body(res);
//	}
//}
//
