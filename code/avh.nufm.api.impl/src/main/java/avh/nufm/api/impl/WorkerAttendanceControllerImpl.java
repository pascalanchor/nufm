package avh.nufm.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Attendance;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class WorkerAttendanceControllerImpl {
	@Autowired NufmRepos repo;
	@Autowired WorkerControllerImpl wrkImpl;
	
	
	
	@Transactional
	public Iterable<Attendance> getALLWorkersAttendance(){
		Iterable<Attendance> res=repo.getAttrepo().findAll();
		return res;
	}
	
	@Transactional
	public Iterable<Attendance> searchAttendanceByName(String workerName){
		//check the worker
		NufmUser wrk=wrkImpl.getWorkerByName(workerName);
		if(wrk==null)
			throw new BusinessException(String.format("error worker name %s", workerName));
		
		System.out.println("the worker name is: "+wrk.getFullName());
		//get all worker's attendances
		Iterable<Attendance> res=repo.getAttrepo().findByNufmUser(wrk);
		
		return res;
	}
}
