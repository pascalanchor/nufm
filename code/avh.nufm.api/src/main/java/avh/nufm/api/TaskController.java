package avh.nufm.api;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.TaskControllerImpl;
import avh.nufm.api.impl.WorkerTaskControllerImpl;
import avh.nufm.api.model.transformer.TaskTransformer;
import avh.nufm.api.model.in.APITaskIn;
import avh.nufm.api.model.out.APITaskOut;
import avh.nufm.business.model.Task;
import avh.nufm.api.common.PathCte;

@RestController
public class TaskController {
	@Autowired private TaskControllerImpl tkimp;
	@Autowired private WorkerTaskControllerImpl wrktskimp;
	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PostMapping(PathCte.AddTaskServletPath)	
	public ResponseEntity<APITaskOut> addTask(@RequestBody APITaskIn tkin){
		try {
			Task tsk=TaskTransformer.taskToModel(tkin);
			System.out.println("in add task api-----------befor");
			APITaskOut res=TaskTransformer.taskFromModel(tkimp.addTask(tsk,tkin.getWorkersName()));
			System.out.println("in add task api-----------after");
			res.setWorkersName(wrktskimp.getWorkersForTask(tsk.getEid()));
			return ResponseEntity.ok().body(res);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@GetMapping(PathCte.GetALlTasksServletPath)
	public ResponseEntity<List<APITaskOut>> getAllTasks(){
		try {
			List<APITaskOut> res=TaskTransformer.listTaskFromItr(tkimp.getAllTasks());
			for(APITaskOut tsk:res) {
				tsk.setWorkersName(wrktskimp.getWorkersForTask(tsk.getEid()));
			}
			return ResponseEntity.ok().body(res);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR','WORKER','OCCUPANT')")
	@GetMapping(PathCte.GetAllDoneTasksServletPath)
	public ResponseEntity<List<APITaskOut>> getAllDoneTasks(@RequestParam String email){
		try {
			List<APITaskOut> res=TaskTransformer.listTaskFromItr(tkimp.getAllDoneTasks(email));
			for(APITaskOut tsk:res) {
				tsk.setWorkersName(wrktskimp.getWorkersForTask(tsk.getEid()));
			}
			return ResponseEntity.ok().body(res);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
		}
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@GetMapping(PathCte.GetTaskByIdServletPath)
	public ResponseEntity<APITaskOut> getTaskById(@RequestParam String id){
		try {
			System.out.print("in get task by id --");
			APITaskOut res=TaskTransformer.taskFromModel(tkimp.getTaskById(id));
			res.setWorkersName(wrktskimp.getWorkersForTask(id));
			return ResponseEntity.ok().body(res);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
		}
	}

	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@PutMapping(PathCte.UpdateTaskServletPath)
	public ResponseEntity<APITaskOut> updateTask(@RequestParam String taskId,@RequestBody APITaskIn newTask){
		try {
			Task tsk=TaskTransformer.taskToModel(newTask);
			APITaskOut res=TaskTransformer.taskFromModel(tkimp.updateTask(taskId, tsk,newTask.getWorkersName()));
			res.setWorkersName(wrktskimp.getWorkersForTask(taskId));
			return ResponseEntity.ok().body(res);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
		}
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@DeleteMapping(PathCte.DeleteTaskServletPath)
	public ResponseEntity<Boolean> deleteTask(@RequestParam String id){
		try {
			
			if(tkimp.deleteTask(id))
				return ResponseEntity.ok().body(true);
			return ResponseEntity.ok().body(false);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
		}
	}
}
