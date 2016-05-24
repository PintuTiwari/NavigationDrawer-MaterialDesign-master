package epbit.exception;

/**
 * @author daffodilsuncity Custom Exception thrown in case of invalid header bar
 *         title
 */
public class InvalidHeaderTitleException extends RuntimeException implements
		IExceptionConstants {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidHeaderTitleException(String headerTitle) {
		super(MESSAGE_INVALID_HEADER_START
				+ headerTitle
				+ (headerTitle == null ? "" : MESSAGE_INVALID_HEADER_MIDDLE
						+ headerTitle.length()));

	}
}
