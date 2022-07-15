package avh.nufm.api.impl.logic;

public enum EInvoiceStatus {

	Paid("Paid"),
	Unpaid("Unpaid");
	
	private String label;
	
	private EInvoiceStatus(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static EInvoiceStatus fromString(String lb) {
		for (EInvoiceStatus res : EInvoiceStatus.values())
			if (res.getLabel().equals(lb))
				return res;
		return null;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}
}
