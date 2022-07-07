package avh.nufm.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import avh.nufm.business.model.Specialization;
import avh.nufm.business.model.repository.NufmRepos;

@Component
public class SpecializationControllerImpl {
	@Autowired private NufmRepos repo;
	
	@Transactional
	public List<String> getWorkerSpecOptions(){
		List<Specialization> options = repo.getSpecrepo().findByType("w");
		List<String> res = new ArrayList<>();
		options.stream().forEach(e->res.add(e.getName()));
		return res;
	}
	
	@Transactional
	public List<String> getContractorSpecOptions(){
		List<Specialization> options = repo.getSpecrepo().findByType("c");
		List<String> res = new ArrayList<>();
		options.stream().forEach(e->res.add(e.getName()));
		return res;
	}
}
