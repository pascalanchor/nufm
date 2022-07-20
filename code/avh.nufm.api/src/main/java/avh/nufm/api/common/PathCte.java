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
}
