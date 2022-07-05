package avh.nufm.api.impl.logic;

import avh.nufm.api.impl.logic.Especialization;

public enum Especialization {

	worker("worker");
	
	private String label;
	
	private Especialization(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static Especialization fromString(String lb) {
		for (Especialization res : Especialization.values())
			if (res.getLabel().equals(lb))
				return res;
		return null;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
