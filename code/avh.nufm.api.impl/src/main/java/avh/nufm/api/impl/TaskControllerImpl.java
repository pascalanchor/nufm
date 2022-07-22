package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.api.impl.logic.ETaskStatus;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.Project;
import avh.nufm.business.model.Task;
import avh.nufm.business.model.TaskType;
import avh.nufm.business.model.UserRole;
import avh.nufm.business.model.WorkerTask;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class TaskControllerImpl {
@Autowired private NufmRepos repo;
@Autowired private WorkerTaskControllerImpl wrktaskCont;


@Transactional
public Task addTask(Task tk ,List<String> WorkersName) {
	if(tk.getName()==null || tk.getName().equals(""))
		throw new BusinessException("you must enter the task name !!");
	
//	if(tk.getDateFrom()==null || tk.getDateTo()==null)
//		throw new BusinessException("you must enter the date_from and the date_to !!");
//	if(tk.getDateFrom().after(tk.getDateTo()))
//		throw new BusinessException("dateFrom cannot be after the date_to");
	//check the task status using enum
	ETaskStatus sts=ETaskStatus.fromString(tk.getStatus());
	if(sts==null)
		throw new BusinessException(String.format("the status :%s not in a valid format !!", tk.getStatus()));
	
	//check the facility
	List<Facility> fcop=repo.getFacrepo().findByName(tk.getFacility().getName());
	if(fcop==null || fcop.isEmpty()) 
		throw new BusinessException(String.format("error facility id:%s", tk.getFacility().getEid()));
	
	//check the project
	List<Project> prjop=repo.getProjrepo().findByName(tk.getProject().getName());
	if(prjop==null || prjop.isEmpty()) 
		throw new BusinessException(String.format("error project id:%s", tk.getProject().getName()));
	//check the type
	List<TaskType> typop=repo.getTasktyperepo().findByName(tk.getTaskType().getName());
	if(typop==null || typop.isEmpty()) 
		throw new BusinessException(String.format("error task type  id:%s", tk.getTaskType().getName()));
	//check if the name is already exist
	List<Task> tsklist=repo.getTaskrepo().findByName(tk.getName());
	if((tsklist!=null)&& (tsklist.size()>0))
		throw new BusinessException(String.format("the task name: %s is already exist !!",tk.getName()));
	List<NufmUser> workers=new ArrayList<NufmUser>();
	List<UserRole> roles;
	
	//check the workers 
	for(String wrkName:WorkersName) {
		List<NufmUser> wrk=repo.getNfuserrepo().findByFullName(wrkName);
		if(wrk==null || wrk.isEmpty())
			throw new BusinessException(String.format("invalid worker id:'%s'",wrkName));
		roles=repo.getUserrolerepo().findByNufmUser(wrk.get(0));
		boolean isworker=false;
		for(UserRole role:roles) {
			if(role.getNufmRole().getName().equals("ROLE_WORKER"))
				isworker=true;
		}
		if(!isworker)
			throw new BusinessException(String.format("the user with fullName:%s does not have a role worker!! ", wrk.get(0).getFullName()));
		isworker=false;
		workers.add(wrk.get(0));
	}
	
	//complete definitions
	tk.setEid(UUID.randomUUID().toString());
	tk.setFacility(fcop.get(0));
	tk.setProject(prjop.get(0));
	tk.setTaskType(typop.get(0));
	//there is issue here with the created at attribute (change from string to timestamp in the db)
	tk.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()).toString());
	//save to db
	repo.getTaskrepo().save(tk);
	//now we can assign the workers
	for(NufmUser w:workers) {
		wrktaskCont.assignWorkerToTask(tk, w);
	}
	
	return tk;
}



public Iterable<Task> getAllTasks(){
	return repo.getTaskrepo().findAll();
}


public Task getTaskById(String id) {
	if(id==null || id.equals(""))
		throw new BusinessException("you must enter the task id !!");
	Optional<Task> tskop=repo.getTaskrepo().findById(id);
	if(tskop==null || tskop.isEmpty())
		throw new BusinessException(String.format("error task id:%s", id));
	
	return tskop.get();
}
	

@Transactional
public Task updateTask(String id,Task newTask,List<String> WorkersName) {
	if(id==null || id.equals(""))
		throw new BusinessException("you must enter the task id !!");
	//check the new task properties
	if(newTask.getName()==null || newTask.getName().equals(""))
		throw new BusinessException("you must enter the task name !!");
	//	if(newTask.getDateFrom()==null || newTask.getDateTo()==null)
	//		throw new BusinessException("you must enter the date_from and the date_to !!");
	//	if(newTask.getDateFrom().after(newTask.getDateTo()))
	//		throw new BusinessException("dateFrom cannot be after the date_to");
	//check the task status using enum
	
	System.out.println("in updateTask impl ---befor check task");
	//get the task by id
	Optional<Task> taskList=repo.getTaskrepo().findById(id);
	if(taskList==null || taskList.isEmpty())
		throw new BusinessException(String.format("error task id :%s", id));
	Task updatedTask=taskList.get();
	System.out.println("in updateTask impl ---after check task1");
	//check the facility
	List<Facility> fcop=repo.getFacrepo().findByName(newTask.getFacility().getName());
	if(fcop==null || fcop.isEmpty()) 
		throw new BusinessException(String.format("error facility id:%s", newTask.getFacility().getEid()));
		
	//check the project
	List<Project> prjop=repo.getProjrepo().findByName(newTask.getProject().getName());
	if(prjop==null || prjop.isEmpty()) 
		throw new BusinessException(String.format("error project id:%s", newTask.getProject().getName()));
	//check the type
	List<TaskType> typop=repo.getTasktyperepo().findByName(newTask.getTaskType().getName());
	if(typop==null || typop.isEmpty()) 
		throw new BusinessException(String.format("error task type  id:%s", newTask.getTaskType().getName()));
	
	//check if the name is already exist
	List<Task> tsklist=repo.getTaskrepo().findByName(newTask.getName());
	System.out.println("in updateTask impl ---after check taskName");
	if((tsklist!=null)&& (tsklist.size()>0)&&(updatedTask.getEid().equals(id)==false))
		throw new BusinessException(String.format("the task name: %s is already exist !!",newTask.getName()));
		
	System.out.println("in updateTask impl ---after check taskName2");
	//check the status
	ETaskStatus sts=ETaskStatus.fromString(newTask.getStatus());
	if(sts==null)
		throw new BusinessException(String.format("the status :%s not in a valid format !!", newTask.getStatus()));
	
	List<NufmUser> workers=new ArrayList<NufmUser>();
	List<UserRole> roles;
	
	//check the workers 
		for(String wrkName:WorkersName) {
			List<NufmUser> wrk=repo.getNfuserrepo().findByFullName(wrkName);
			if(wrk==null || wrk.isEmpty())
				throw new BusinessException(String.format("invalid worker name:'%s'",wrkName));
			roles=repo.getUserrolerepo().findByNufmUser(wrk.get(0));
			boolean isworker=false;
			for(UserRole role:roles) {
				if(role.getNufmRole().getName().equals("ROLE_WORKER"))
					isworker=true;
			}
			if(!isworker)
				throw new BusinessException(String.format("the user with fullName:%s does not have a role worker!! ", wrk.get(0).getFullName()));
			isworker=false;
			workers.add(wrk.get(0));
		}
	
	//complete definitions
	updatedTask.setFacility(fcop.get(0));
	updatedTask.setName(newTask.getName());
	updatedTask.setProject(prjop.get(0));
	updatedTask.setTaskType(typop.get(0));
	updatedTask.setCreatedAt(newTask.getCreatedAt());
	updatedTask.setComment(newTask.getComment());
	updatedTask.setStatus(newTask.getStatus());
	
	//we need to delete the old workers then assign new workers
	List<WorkerTask> oldWorkers=repo.getWorktaskrepo().findByTask(updatedTask);
	for(WorkerTask itr:oldWorkers) {
		repo.getWorktaskrepo().deleteById(itr.getEid());
			}
	
	//save the task
	System.out.println(String.format("the updated task id :%s", updatedTask.getEid()));
	
	repo.getTaskrepo().save(updatedTask);
	
	
	//now assign the new workers
	for(NufmUser w:workers) {
		wrktaskCont.assignWorkerToTask(updatedTask, w);
	}
	
	return updatedTask;
}

@Transactional
public boolean deleteTask(String id) {
	
	if(id==null || id.equals(""))
		throw new BusinessException("you must enter the task id !!");
	//check the id
	Optional<Task> tskOp=repo.getTaskrepo().findById(id);
	if(tskOp==null || tskOp.isEmpty())
		throw new BusinessException(String.format("invalid task id :%s", id));
	repo.getTaskrepo().deleteById(id);
	//check if deleted
	Optional<Task> deletedtsk=repo.getTaskrepo().findById(id);
	if((deletedtsk!=null)&&!(deletedtsk.isEmpty()))
		return false;
	return true;
}
}
