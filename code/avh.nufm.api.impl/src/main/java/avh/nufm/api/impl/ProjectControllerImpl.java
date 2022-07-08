package avh.nufm.api.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.api.impl.logic.EProjectStatus;
import avh.nufm.business.model.Contractor;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.Project;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class ProjectControllerImpl {
@Autowired private NufmRepos repo;


public Project addProject(Project prj){
	
	if(prj.getFacility().getEid().equals("")
			|| prj.getContractor().getEid().equals("")
			|| prj.getName().equals(""))
		throw new BusinessException("name,contractor and faclity cannot be null");
		
	//check the facility
	Optional<Facility> fcop=repo.getFacrepo().findById(prj.getFacility().getEid());
	if(fcop==null || fcop.isEmpty())
		throw new BusinessException(String.format("error facility id:%s", prj.getFacility().getEid()));
	Facility fc=fcop.get();
	//check the contractor
	Optional<Contractor> contop=repo.getContrepo().findById(prj.getContractor().getEid());
	if(contop ==null || contop.isEmpty())
		throw new BusinessException(String.format("error contractor id :%s", prj.getContractor().getEid()));
	Contractor cont=contop.get();
	EProjectStatus sts=EProjectStatus.fromString(prj.getStatus());
	if(sts==null)
		throw new BusinessException(String.format("invalid status :'%s' format", prj.getStatus()));
	//complete entity definition
	prj.setEid(UUID.randomUUID().toString());
	prj.setFacility(fc);
	prj.setContractor(cont);
	
	//save to db
	repo.getProjrepo().save(prj);
	return prj;
}

public Iterable<Project> getAllProjects(){
Iterable<Project> res=repo.getProjrepo().findAll();
return res;
}


public Project editProject(String prId,Project newPrj){
	return null;
}


public Project getProjectById(String Id) {
	if(Id.equals(""))
		throw new BusinessException("you must enter the project id!!");
	Optional<Project> prjop=repo.getProjrepo().findById(Id);
	if(prjop==null || prjop.isEmpty())
		throw new BusinessException(String.format("error project Id:%s", Id));
	Project res=prjop.get();
	
	return res;
}


}
