//package avh.nufm.api;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.server.ResponseStatusException;
//
//import avh.nufm.api.impl.ProjectControllerImpl;
//import avh.nufm.api.model.Transformer;
//import avh.nufm.api.model.in.APIProjectIn;
//import avh.nufm.api.model.out.APIProjectOut;
//import avh.nufm.business.model.Project;
//
//@RestController
//public class ProjectController {
//@Autowired private ProjectControllerImpl prjIm;
//
//
//@PostMapping("avh/nufm/v1/private/project/add")
//public ResponseEntity<APIProjectOut> addProject(@RequestBody APIProjectIn prjIn){
//	try {
//		APIProjectOut res;
//		Project prj=Transformer.ProjectToModel(prjIn);
//		res=Transformer.ProjectFromModel(prjIm.addProject(prj));
//		return ResponseEntity.ok().body(res);
//	}catch (Exception e) {
//		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
//	}
//	
//}
//
//@GetMapping("avh/nufm/v1/private/projects")
//public ResponseEntity<List<APIProjectOut>> getAllProjects(){
//	try {
//		List<APIProjectOut> res;
//		res=Transformer.prjListFromIterable(prjIm.getAllProjects());
//		return ResponseEntity.ok().body(res);
//	} catch (Exception e) {
//		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
//	}
//}
//
//
//@GetMapping("avh/nufm/v1/private/projects/{ProjectId}")
//public ResponseEntity<APIProjectOut> getProjectById(@PathVariable("ProjectId") String  prId){
//	try {
//		APIProjectOut res=Transformer.ProjectFromModel(prjIm.getProjectById(prId));
//		return ResponseEntity.ok().body(res);
//		
//	} catch (Exception e) {
//		throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
//	}
//}
//	
//}
