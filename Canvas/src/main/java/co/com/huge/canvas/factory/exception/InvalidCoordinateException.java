package co.com.huge.canvas.factory.exception;

import co.com.huge.canvas.CanvasBusinessException;

public class InvalidCoordinateException extends CanvasBusinessException {

	private static final long serialVersionUID = 1L;
	
	public InvalidCoordinateException(String errorMessage) {
		super(errorMessage);
	}
	
}
