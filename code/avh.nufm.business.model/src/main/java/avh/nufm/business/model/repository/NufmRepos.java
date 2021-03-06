package avh.nufm.business.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NufmRepos {

	//initialize all the repositories
	
	@Autowired
	private AttendanceRepo attrepo;
	@Autowired
	private ContractoRepo contrepo;
	@Autowired
	private EquipmentRepo eqmtrepo;
	@Autowired
	private FacilityRepo facrepo;
	@Autowired
	private FacilityTypeRepo factyperepo;
	@Autowired
	private InvoiceRepo invrepo;
	@Autowired
	private NufmRoleRepo nfrolerepo;
	@Autowired
	private NufmUserRepo nfuserrepo;
	@Autowired
	private ProjectRepo projrepo;
	@Autowired
	private ProjectWorkerRepo projworkrepo;
	@Autowired
	private SafetyMaterialRepo safmtrrepo;
	@Autowired
	private SafetyWorkerRepo safworkrepo;
	@Autowired
	private SpecializationRepo specrepo;
	@Autowired
	private TaskNotificationRepo tasknotrepo;
	@Autowired
	private TaskRepo taskrepo;
	@Autowired
	private TaskRequestRepo taskreqrepo;
	@Autowired
	private TaskTypeRepo tasktyperepo;
	@Autowired
	private UserRoleRepo userrolerepo;
	@Autowired
	private WorkerScheduleRepo workschrepo;
	@Autowired
	private WorkerTaskRepo worktaskrepo;
	
	
	// generate getters 
	public AttendanceRepo getAttrepo() {
		return attrepo;
	}
	public ContractoRepo getContrepo() {
		return contrepo;
	}
	public EquipmentRepo getEqmtrepo() {
		return eqmtrepo;
	}
	public FacilityRepo getFacrepo() {
		return facrepo;
	}
	public FacilityTypeRepo getFactyperepo() {
		return factyperepo;
	}
	public InvoiceRepo getInvrepo() {
		return invrepo;
	}
	public NufmRoleRepo getNfrolerepo() {
		return nfrolerepo;
	}
	public NufmUserRepo getNfuserrepo() {
		return nfuserrepo;
	}
	public ProjectRepo getProjrepo() {
		return projrepo;
	}
	public ProjectWorkerRepo getProjworkrepo() {
		return projworkrepo;
	}
	public SafetyMaterialRepo getSafmtrrepo() {
		return safmtrrepo;
	}
	public SafetyWorkerRepo getSafworkrepo() {
		return safworkrepo;
	}
	public SpecializationRepo getSpecrepo() {
		return specrepo;
	}
	public TaskNotificationRepo getTasknotrepo() {
		return tasknotrepo;
	}
	public TaskRepo getTaskrepo() {
		return taskrepo;
	}
	public TaskRequestRepo getTaskreqrepo() {
		return taskreqrepo;
	}
	public TaskTypeRepo getTasktyperepo() {
		return tasktyperepo;
	}
	public UserRoleRepo getUserrolerepo() {
		return userrolerepo;
	}
	public WorkerScheduleRepo getWorkschrepo() {
		return workschrepo;
	}
	public WorkerTaskRepo getWorktaskrepo() {
		return worktaskrepo;
	}
	
	
	
	
	
}
