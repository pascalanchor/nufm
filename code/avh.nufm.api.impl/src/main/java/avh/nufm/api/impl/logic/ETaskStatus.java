package avh.nufm.api.impl.logic;

public enum ETaskStatus {
    inprogress("inprogress"),
	finished("finished");
	
	private String label;
	
	private ETaskStatus(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static ETaskStatus fromString(String lb) {
		for (ETaskStatus res : ETaskStatus.values())
			if (res.getLabel().equals(lb))
				return res;
		return null;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
