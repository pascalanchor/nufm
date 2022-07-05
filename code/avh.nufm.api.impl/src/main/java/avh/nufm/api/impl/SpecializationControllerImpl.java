package avh.nufm.api.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.Specialization;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class SpecializationControllerImpl {
@Autowired private NufmRepos repo;

public Specialization addSpecialization(Specialization spec)
{
	if(spec.getName().equals(""))
		throw new BusinessException("you must enter the specialization name !!");
	//check the name if duplicated
	List<Specialization> splist=repo.getSpecrepo().findByName(spec.getName());
	if((splist.size()>0) && (splist!=null))
		throw new BusinessException(String.format("the specialization name:%s is already exist !!", spec.getName()));
	//now we can add the specialization
	spec.setEid(UUID.randomUUID().toString());
	repo.getSpecrepo().save(spec);
	
	return spec;
}

}
