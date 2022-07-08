package avh.nufm.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import avh.nufm.api.impl.errors.BusinessException;
import avh.nufm.business.model.FacilityType;
import avh.nufm.business.model.repository.NufmRepos;
	
@Component
public class FacilityTypeControllerImpl {
	@Autowired private NufmRepos repo;
	
	public FacilityType addFacType(FacilityType fact) {
		if(fact.getName().equals(""))
			throw new BusinessException("you must enter the type name !!");
		//check if the name is duplicated
		List<FacilityType> fclist=repo.getFactyperepo().findByName(fact.getName());
		if((fclist!=null)&& (fclist.size()>0))
			throw new BusinessException(String.format("the type name:%s must be unique ", fact.getName()));
		//save to db
		fact.setEid(UUID.randomUUID().toString());
		repo.getFactyperepo().save(fact);
		
		return fact;
	}
	
	
	public FacilityType getById(String factId) {
		if(factId.equals(""))
			throw new BusinessException("you must enter the type id !!");
		Optional<FacilityType> factop=repo.getFactyperepo().findById(factId);
		if(factop.isEmpty())
			throw new BusinessException(String.format("error type id:%s", factId));
		return factop.get();
	}
	
	
	public Iterable<FacilityType> getAllFacilityTypes()
	{
		Iterable<FacilityType> factlist=repo.getFactyperepo().findAll();
		return factlist;
	}
	
}
