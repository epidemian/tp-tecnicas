package model.core;

public class BusinessLogicException extends RuntimeException {

	private static final long serialVersionUID = 1927603336142125883L;

	public BusinessLogicException() {
		super();
	}

	public BusinessLogicException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessLogicException(String message) {
		super(message);
	}

	public BusinessLogicException(Throwable cause) {
		super(cause);
	}

}
