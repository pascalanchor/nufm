package avh.nufm.api.impl.logic;

public enum EProjectStatus {
	
	inprogress("inprogress");
	
	private String label;
	
	private EProjectStatus(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static EProjectStatus fromString(String lb) {
		for (EProjectStatus res : EProjectStatus.values())
			if (res.getLabel().equals(lb))
				return res;
		return null;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
