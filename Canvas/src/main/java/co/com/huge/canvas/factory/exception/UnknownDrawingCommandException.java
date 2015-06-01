package co.com.huge.canvas.factory.exception;

import co.com.huge.canvas.CanvasBusinessException;

public class UnknownDrawingCommandException extends CanvasBusinessException {

	private static final long serialVersionUID = 1L;
	
	public UnknownDrawingCommandException(String errorMessage) {
		super(errorMessage);
	}
}
