package exceptions;

public class ZerviceRequestsNotEmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2516397851102259615L;

	public ZerviceRequestsNotEmptyException() {
		super("zervice.delete.error.requestsNotEmpty");
	}


}
