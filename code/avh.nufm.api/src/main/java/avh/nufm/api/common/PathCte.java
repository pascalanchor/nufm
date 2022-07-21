package avh.nufm.api.common;

public interface PathCte {
	//Private path
	public static final String PrivateServletPath = "avh/nufm/v1/private";
	//worker management
	public static final String WorkerServletPath = PrivateServletPath+"/worker";
	public static final String AddWorkerServletPath = WorkerServletPath;
	public static final String EditWorkerServletPath = WorkerServletPath+"/edit";
	public static final String DeleteWorkerServletPath = WorkerServletPath+"/delete";
	public static final String GetWorkersServletPath = PrivateServletPath+"/workers";
	public static final String GetWorkerByIdServletPath = WorkerServletPath;
	//contractor management
	public static final String ContractorServletPath = PrivateServletPath+"/contractor";
	public static final String AddContractorServletPath = ContractorServletPath;
	public static final String EditContractorServletPath = ContractorServletPath+"/edit";
	public static final String DeleteContractorServletPath = ContractorServletPath+"/delete";
	public static final String GetContractorsServletPath = PrivateServletPath+"/contractors";
	public static final String GetContractorByIdServletPath = ContractorServletPath;
	//facility management 
	public static final String FacilityServletPath = PrivateServletPath+"/facility";
	public static final String AddFacilityServletPath = FacilityServletPath;
	public static final String EditFacilityServletPath = FacilityServletPath+"/edit";
	public static final String DeleteFacilityServletPath = FacilityServletPath+"/delete";
	public static final String GetFacilitiesServletPath = PrivateServletPath+"/facilities";
	public static final String GetFacilityByIdServletPath = FacilityServletPath;
	//notification 
	public static final String NotificationServletPath = PrivateServletPath+"/notification";
	public static final String AddNotificationServletPath = NotificationServletPath;
	public static final String GetNotificationsByIdServletPath = PrivateServletPath+"/notifications";	
	//task management
	public static final String AddTaskServletPath =PrivateServletPath +"/task/add";
	public static final String GetALlTasksServletPath =PrivateServletPath +"/tasks";
	public static final String GetTaskByIdServletPath =PrivateServletPath +"/task/getById";
	public static final String UpdateTaskServletPath =PrivateServletPath +"/task/update";
	public static final String DeleteTaskServletPath =PrivateServletPath +"/task/delete";
	
	//project management
	public static final String AddProjectServletPath =PrivateServletPath +"/project/add";
	public static final String GetAllProjectsServletPath =PrivateServletPath +"/projects";
	public static final String GetProjectByIdServletPath =PrivateServletPath +"/projects/{ProjectId}";
	//attendance management
	public static final String GetAllWorkersAttendancesServletPath =PrivateServletPath +"/attendances";
	public static final String SearchWorkersAttendancesServletPath =PrivateServletPath +"/attendances/search";
	
	//invoice management
	public static final String AddInvoiceServletPath =PrivateServletPath +"/invoice/add";
	public static final String UpdateInvoiceServletPath =PrivateServletPath +"/invoice/update";
	public static final String DeleteInvoiceServletPath =PrivateServletPath +"/invoice/delete";
	public static final String GetAllInvoiceServletPath =PrivateServletPath +"/invoices";
	public static final String SearchInvoiceServletPath =PrivateServletPath +"/invoice/search";
	
	//safetyMaterial management
	public static final String AddSafetyMaterialServletPath =PrivateServletPath +"/safetyMaterial/add";
	public static final String GetAllSafetyMaterialsServletPath =PrivateServletPath +"/safetyMaterials";
	public static final String UpdateSafetyMaterialServletPath =PrivateServletPath +"/safetyMaterial/update";
	public static final String deleteSafetyMaterialServletPath =PrivateServletPath +"/safetyMaterial/delete";
}
