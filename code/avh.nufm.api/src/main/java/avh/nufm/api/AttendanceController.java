package avh.nufm.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import avh.nufm.api.impl.WorkerAttendanceControllerImpl;
import avh.nufm.api.model.out.APIAttendanceOut;
import avh.nufm.api.model.transformer.AttendanceTransformer;
import avh.nufm.api.common.PathCte;

@RestController
public class AttendanceController {

	@Autowired WorkerAttendanceControllerImpl atcimp;
	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@GetMapping(PathCte.GetAllWorkersAttendancesServletPath)
	public ResponseEntity<List<APIAttendanceOut>> getAllWorkersAttendance(){
		try {
			List<APIAttendanceOut> res=AttendanceTransformer.
					listAttendanceFromIterable(atcimp.getALLWorkersAttendance());
			return ResponseEntity.ok().body(res);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','CONTRACTOR')")
	@GetMapping(PathCte.SearchWorkersAttendancesServletPath)
	public ResponseEntity<List<APIAttendanceOut>> SearchAttendanceByName(
			@RequestParam String workerName){
		try {
			List<APIAttendanceOut> res=AttendanceTransformer.
					listAttendanceFromIterable(atcimp.searchAttendanceByName(workerName));
			return ResponseEntity.ok().body(res);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
		}
	}
}
