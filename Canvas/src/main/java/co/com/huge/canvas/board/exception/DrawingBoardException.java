package co.com.huge.canvas.board.exception;

import co.com.huge.canvas.CanvasBusinessException;

public class DrawingBoardException extends CanvasBusinessException {

	private static final long serialVersionUID = 1L;

	public DrawingBoardException(String errorMessage) {
		super(errorMessage);
	}
}
