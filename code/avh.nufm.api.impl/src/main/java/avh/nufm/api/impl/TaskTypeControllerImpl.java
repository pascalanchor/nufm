package avh.nufm.api.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.TaskType;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class TaskTypeControllerImpl {

	@Autowired private NufmRepos repo;
	
	@Transactional
	public TaskType addTaskType(TaskType tst) {
		
		if(tst.getName().equals(""))
			throw new BusinessException("task type name cannot be empty !!");
		Optional<TaskType> tstop=repo.getTasktyperepo().findById(tst.getEid());
		if(tstop==null || tstop.isEmpty())
			throw new BusinessException(String.format("task type name:%s is already exist !!", tst.getName()));
		tst.setEid(UUID.randomUUID().toString());
		repo.getTasktyperepo().save(tst);
		return tst;
	}
	
	public Iterable<TaskType> getAllTaskType(){
		return repo.getTasktyperepo().findAll();
	}
	
	
}
