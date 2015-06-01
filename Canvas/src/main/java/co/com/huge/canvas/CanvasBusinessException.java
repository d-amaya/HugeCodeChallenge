package co.com.huge.canvas;

public class CanvasBusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public CanvasBusinessException(String errorMessage) {
		super(errorMessage);
	}
	
	public CanvasBusinessException(String errorMessage, Throwable e) {
		super(errorMessage, e);
	}
}
