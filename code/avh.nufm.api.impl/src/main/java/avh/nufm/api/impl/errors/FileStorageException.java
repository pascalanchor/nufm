package avh.nufm.api.impl.errors;

public class FileStorageException extends RuntimeException {
	private static final long serialVersionUID = -4317307318079321456L;

	public FileStorageException(String msg) {
		super(msg);
	}
}
