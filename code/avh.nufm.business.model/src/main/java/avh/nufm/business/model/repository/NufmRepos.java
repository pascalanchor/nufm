package avh.nufm.business.model.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;
@Getter
@Component
public class NufmRepos {

	//initialize all the repositories
	
	@Autowired
	private AttendanceRepo attrepo;
	@Autowired
	private EquipmentRepo eqmtrepo;
	@Autowired
	private FacilityEquipmentRepo facilityEquipmentRepo;
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
	private TaskRepo taskrepo;
	@Autowired
	private TaskTypeRepo tasktyperepo;
	@Autowired
	private UserRoleRepo userrolerepo;
	@Autowired
	private WorkerScheduleRepo workschrepo;
	@Autowired
	private WorkerTaskRepo worktaskrepo;
	@Autowired
	private ConfirmationTokenRepo confirmationTokenrepo;
	@Autowired
	private UserSpecializationRepo userSpecrepo;
	@Autowired
	private NotificationRepo notificationRepo;
	@Autowired
	private SafetyMaterialTyeRepo safmtrtyperepo;
	@Autowired
	private DocumentRepo documentRepo;
	@Autowired
	private BudgetRepo budgetRepo;
	@Autowired
	private BudgetTypeRepo budgetTypeRepo;
	@Autowired
	private VendorRepo vendorRepo;
	@Autowired
	private ContractRepo contractRepo;
	
	// generate getters 
	public AttendanceRepo getAttrepo() {
		return attrepo;
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

	public TaskRepo getTaskrepo() {
		return taskrepo;
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
	public ConfirmationTokenRepo getConfirmationTokenRepo() {
		return confirmationTokenrepo;
	}
	
	public UserSpecializationRepo getUserSpecrepo() {
		return userSpecrepo;
	}
	
	public SafetyMaterialTyeRepo getSafmtrTyperepo() {
		return safmtrtyperepo;
	}
	public void setFacilityEquipmentRepo(FacilityEquipmentRepo facilityEquipmentRepo) {
		this.facilityEquipmentRepo = facilityEquipmentRepo;
	}
	public void setDocumentRepo(DocumentRepo documentRepo) {
		this.documentRepo = documentRepo;
	}
	public void setBudgetRepo(BudgetRepo budgetRepo) {
		this.budgetRepo = budgetRepo;
	}
	public void setVendorRepo(VendorRepo vendorRepo) {
		this.vendorRepo = vendorRepo;
	}
	public void setContractRepo(ContractRepo contractRepo) {
		this.contractRepo = contractRepo;
	}
	public void setBudgetTypeRepo(BudgetTypeRepo budgetTypeRepo) {
		this.budgetTypeRepo = budgetTypeRepo;
	}
	
	
}
