package co.com.huge.canvas.factory.exception;

import co.com.huge.canvas.CanvasBusinessException;

public class DrawingCommandFactoryException extends CanvasBusinessException {

private static final long serialVersionUID = 1L;
	
	public DrawingCommandFactoryException(String errorMessage) {
		super(errorMessage);
	}
	
	public DrawingCommandFactoryException(String errorMessage, Throwable e) {
		super(errorMessage, e);
	}
}
