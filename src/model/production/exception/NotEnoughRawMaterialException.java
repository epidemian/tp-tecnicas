package model.production.exception;

public class NotEnoughRawMaterialException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughRawMaterialException() {
		super();
	}

	public NotEnoughRawMaterialException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotEnoughRawMaterialException(String arg0) {
		super(arg0);
	}

	public NotEnoughRawMaterialException(Throwable arg0) {
		super(arg0);
	}

}
