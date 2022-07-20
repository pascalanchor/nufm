package avh.nufm.api.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.Task;
import avh.nufm.business.model.WorkerTask;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class WorkerTaskControllerImpl {
@Autowired private NufmRepos repo;


public WorkerTask assignWorker(Task tk,NufmUser worker) {
	//check the task
	Optional<Task> tskop=repo.getTaskrepo().findById(tk.getEid());
	if(tskop==null || tskop.isEmpty())
		throw new BusinessException(String.format("invalid task with id:%s", tk.getEid()));
	//check the workers
	
	Optional<NufmUser> wrk=repo.getNfuserrepo().findById(worker.getEid());
	if(wrk==null || wrk.isEmpty()) 
		throw new BusinessException(String.format("invalid worker id:%s", worker.getEid()));
		
	//complete definition
	WorkerTask wrktask=new WorkerTask();
	wrktask.setEid(UUID.randomUUID().toString());
	wrktask.setTask(tk);
	wrktask.setAssignedDate(Timestamp.valueOf(LocalDateTime.now()));
	wrktask.setNufmUser(worker);
	
	
	repo.getWorktaskrepo().save(wrktask);
	return wrktask;
	
	
}

public List<String> getWorkersForTask(String tskId) {
	
	Optional<Task> tskop=repo.getTaskrepo().findById(tskId);
	if(tskop==null || tskop.isEmpty())
		throw new BusinessException(String.format("invalid task id:%s", tskId));
	//create list of workers names
	List<String> workers=new ArrayList<String>();
	
	for(WorkerTask itr:repo.getWorktaskrepo().findByTask(tskop.get())) {
		workers.add(itr.getNufmUser().getFullName());
	}
	
	return workers;
}
	
}
