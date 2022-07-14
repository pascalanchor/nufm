package avh.nufm.security.common;

public interface SecurityCte {
	//public path
	public static final String PublicServletPath = "/avh/nufm/v1/public";
	//login path
	public static final String LoginServletPath = PublicServletPath + "/login";
	//register path
	public static final String RegisterServletPath = PublicServletPath + "/register";
	//reset password path
	public static final String ResetPasswordServletPath = PublicServletPath + "/resetPassword";
	//forget password path
	public static final String ForgetPasswordServletPath = PublicServletPath + "/forgetPassword";
	//register worker path
	public static final String RegisterWorkerServletPath = PublicServletPath + "/registerWorker";
	//register contractor path
	public static final String RegisterContractorServletPath = PublicServletPath + "/registerContractor";
	//Roles or UserTypes
	public static final String RoleSupervisor = "ROLE_SUPERVISOR";
	public static final String RoleOwner = "ROLE_OWNER";
	public static final String RoleWorker = "ROLE_WORKER";
	public static final String RolePuchasingOfficer = "ROLE_PURCHASINGOFFICER";
	public static final String RoleAdmin = "ROLE_ADMIN";
	public static final String RoleOccupant = "ROLE_OCCUPANT";
	public static final String RoleContractor = "ROLE_CONTRACTOR";
	//Private path
	public static final String PrivateServletPath = "/avh/nufm/v1/private";

}
