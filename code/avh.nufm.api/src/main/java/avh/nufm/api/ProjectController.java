package avh.nufm.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.ProjectControllerImpl;
import avh.nufm.api.model.in.APIProjectIn;
import avh.nufm.api.model.out.APIProjectOut;
import avh.nufm.api.model.transformer.ProjectTransformer;
import avh.nufm.business.model.Project;
import avh.nufm.api.common.PathCte;

@RestController
public class ProjectController {
@Autowired private ProjectControllerImpl prjIm;


@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@PostMapping(PathCte.AddProjectServletPath)
public ResponseEntity<APIProjectOut> addProject(@RequestBody APIProjectIn prjIn){
	try {
		APIProjectOut res;
		Project prj=ProjectTransformer.projectToModel(prjIn);
		System.out.println("-111-11-11-1-1-111-1--1-1--1-1");
		res=ProjectTransformer.projectFromModel(prjIm.addProject(prj));
		System.out.println("77777777777777777777777");
		return ResponseEntity.ok().body(res);
	}catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
	
}


@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping(PathCte.GetAllProjectsServletPath)
public ResponseEntity<List<APIProjectOut>> getAllProjects(){
	try {
		List<APIProjectOut> res;
		res=ProjectTransformer.prjListFromIterable(prjIm.getAllProjects());
		return ResponseEntity.ok().body(res);
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}

@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
@GetMapping(PathCte.GetProjectByIdServletPath)
public ResponseEntity<APIProjectOut> getProjectById(@PathVariable("ProjectId") String  prId){
	try {
		APIProjectOut res=ProjectTransformer.projectFromModel(prjIm.getProjectById(prId));
		return ResponseEntity.ok().body(res);
		
	} catch (Exception e) {
		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
	}
}
	
}
