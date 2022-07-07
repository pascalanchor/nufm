package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.Project;
import avh.nufm.business.model.Task;
import avh.nufm.business.model.TaskType;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class TaskControllerImpl {
@Autowired private NufmRepos repo;

public Task addTask(Task tk) {
	if(tk.getName()==null || tk.getName().equals(""))
		throw new BusinessException("you must enter the task name !!");
	if(tk.getDateFrom()==null || tk.getDateTo()==null)
		throw new BusinessException("you must enter the date_from and the date_to !!");
	if(tk.getDateFrom().after(tk.getDateTo()))
		throw new BusinessException("dateFrom cannot be after the date_to");
	//check the task status using enum
	//
	//
	//check the facility
	Optional<Facility> fcop=repo.getFacrepo().findById(tk.getFacility().getEid());
	if(fcop==null || fcop.isEmpty()) 
		throw new BusinessException(String.format("error facility id:%s", tk.getFacility().getEid()));
	
	//check the project
	Optional<Project> prjop=repo.getProjrepo().findById(tk.getProject().getEid());
	if(prjop==null || prjop.isEmpty()) 
		throw new BusinessException(String.format("error project id:%s", tk.getProject().getEid()));
	//check the type
	Optional<TaskType> typop=repo.getTasktyperepo().findById(tk.getTaskType().getEid());
	if(typop==null || typop.isEmpty()) 
		throw new BusinessException(String.format("error task type  id:%s", tk.getTaskType().getEid()));
	
	//complete definitions
	tk.setEid(UUID.randomUUID().toString());
	tk.setFacility(fcop.get());
	tk.setProject(prjop.get());
	tk.setTaskType(typop.get());
	//there is issue here with the created at attribute (change from string to timestamp in the db)
	tk.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()).toString());
	//save to db
	repo.getTaskrepo().save(tk);
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
	
	
}
