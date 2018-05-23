package exception;

public class NoSuchRowException extends Exception {

	private static final long serialVersionUID = 8794914178282421146L;
	
	public NoSuchRowException() {
		super("Element existiert nicht.");
	}
	
	public NoSuchRowException(final String message) {
		super(message);
	}

}
