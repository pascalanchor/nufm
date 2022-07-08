//package avh.nufm.api;
//
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//
//import avh.nufm.api.impl.TaskControllerImpl;
//import avh.nufm.api.model.Transformer;
//import avh.nufm.api.model.in.APITaskIn;
//import avh.nufm.api.model.out.APITaskOut;
//import avh.nufm.business.model.Task;
//
//@RestController
//public class TaskController {
//	@Autowired private TaskControllerImpl tkimp;
//	
//	
//	@PostMapping("avh/nufm/v1/private/task/add")	
//	public ResponseEntity<APITaskOut> addTask(APITaskIn tkin){
//		try {
//			Task tsk=Transformer.TaskToModel(tkin);
//			APITaskOut res=Transformer.TastFromModel(tkimp.addTask(tsk));
//			return ResponseEntity.ok().body(res);
//		}catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
//		}
//	}
//	
//	
//	@GetMapping("avh/nufm/v1/private/tasks")
//	public ResponseEntity<List<APITaskOut>> getAllTasks(){
//		try {
//			List<APITaskOut> res=Transformer.listTaskFromItr(tkimp.getAllTasks());
//			return ResponseEntity.ok().body(res);
//		} catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
//		}
//	}
//	
//	
//	@GetMapping("avh/nufm/v1/private/tasks/{taskId}")
//	public ResponseEntity<APITaskOut> getTaskById(@PathVariable("taskId") String id){
//		try {
//			System.out.print("in get task by id --");
//			APITaskOut res=Transformer.TastFromModel(tkimp.getTaskById(id));
//			return ResponseEntity.ok().body(res);
//		}catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
//		}
//	}
//
//	@PutMapping("avh/nufm/v1/private/task/update/{taskId}")
//	public ResponseEntity<APITaskOut> updateTask(@PathVariable("taskId") String taskId,@RequestBody APITaskIn newTask){
//		try {
//			Task tsk=Transformer.TaskToModel(newTask);
//			APITaskOut res=Transformer.TastFromModel(tkimp.updateTask(taskId, tsk));
//			return ResponseEntity.ok().body(res);
//		}catch(Exception e) {
//			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
//		}
//	}
//}
