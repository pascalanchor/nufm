package avh.nufm.api.impl.logic;

public enum EMaterialStatus {
used("used"),
free("free");
	
	private String label;
	
	private EMaterialStatus(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static EMaterialStatus fromString(String lb) {
		for (EMaterialStatus res : EMaterialStatus.values())
			if (res.getLabel().equals(lb))
				return res;
		return null;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
