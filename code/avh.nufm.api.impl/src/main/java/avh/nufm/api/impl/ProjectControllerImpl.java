package avh.nufm.api.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.api.impl.logic.EProjectStatus;
import avh.nufm.business.model.Facility;
import avh.nufm.business.model.NufmUser;
import avh.nufm.business.model.Project;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class ProjectControllerImpl {
@Autowired private NufmRepos repo;

@Transactional
public Project addProject(Project prj){
	
//	if(prj.getFacility().getEid().equals("")
//			|| prj.getContractor().getEid().equals("")
//			|| prj.getName().equals(""))
//		throw new BusinessException("name,contractor and faclity cannot be null");
		
	//check the facility
	System.out.println("00000000000000000000000000000");
	Optional<Facility> fcop=repo.getFacrepo().findById(prj.getFacility().getEid());
	if(fcop==null || fcop.isEmpty())
		throw new BusinessException(String.format("error facility id:%s", prj.getFacility().getEid()));
	System.out.println("111111111111111111111111111");
	Facility fc=fcop.get();
	//check the contractor
	Optional<NufmUser> contop=repo.getNfuserrepo().findById(prj.getNufmUser().getEid());
	if(contop ==null || contop.isEmpty())
		throw new BusinessException(String.format("error contractor id :%s", prj.getNufmUser().getEid()));
	System.out.println("222222222222222222222222222");
	NufmUser cont=contop.get();
	EProjectStatus sts=EProjectStatus.fromString(prj.getStatus());
	if(sts==null)
		throw new BusinessException(String.format("invalid status :'%s' format", prj.getStatus()));
	
	System.out.println("3333333333333333333333333333333");
	//complete entity definition
	prj.setEid(UUID.randomUUID().toString());
	prj.setFacility(fc);
	prj.setNufmUser(cont);
	System.out.println("44444444444444444444444444444");
	//save to db
	repo.getProjrepo().save(prj);
	System.out.println("5555555555555555555555555555555");
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
