package co.com.huge.canvas.board;

import java.util.ArrayList;
import java.util.Collection;

import co.com.huge.canvas.bo.CanvasCommand;
import co.com.huge.canvas.bo.DrawingCommand;
import co.com.huge.canvas.bo.RectangleCommand;
import co.com.huge.canvas.board.constant.BoardConstants;
import co.com.huge.canvas.board.exception.DrawingBoardException;
import co.com.huge.canvas.factory.constant.FactoryConstants.TypeWritingPaintingCommand;

public class DrawingBoard {

	private Collection<DrawingCommand> commandsExecuted = null;
	private String[][] board = null;
	
	public DrawingBoard(CanvasCommand canvasCommand) {
		initializeBoard(canvasCommand);
	}
	
	public void drawCommandOnBoard(DrawingCommand drawingCommand) throws DrawingBoardException {
		drawingCommand.drawCommand(this);
		commandsExecuted.add(drawingCommand);
	}
	
	public int getRealNumberRowsBoard() {
		if (board != null) {
			return board.length;
		}
		return 0;
	}
	
	public int getRealNumberColumnsBoard() {
		if (board != null) {
			return board[0].length;
		}
		return 0;
	}
	
	public int getNumberRowsPaintingArea() {
		if (board != null) {
			return board.length - BoardConstants.CANTIDAD_CELDAS_ADICIONALES_DEFINICION_TABLERO;
		}
		return 0;
	}
	
	public int getNumberColumnsPaintingArea() {
		if (board != null) {
			return board[0].length - BoardConstants.CANTIDAD_CELDAS_ADICIONALES_DEFINICION_TABLERO;
		}
		return 0;
	}
	
	public String getValue(int r, int c) {
		return board[r][c];
	}
	
	public void setValue(int r, int c, String value, TypeWritingPaintingCommand typePaintingOnBoard) throws DrawingBoardException {
		if (TypeWritingPaintingCommand.TYPE_WRITING_PAINTING_AREA == typePaintingOnBoard) {
			if(!validatePositionWithinPaintingArea(r, c)) {
				throw new DrawingBoardException("The comand's coordinates are out of the painting area.");
			} 	
		}
		board[r][c] = value;
	}
	
	public void printDrawingBoard() {
		System.out.println(convertBoard2StringBuilder().toString());
	}
	
	public StringBuilder convertBoard2StringBuilder() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < getRealNumberRowsBoard(); i++) {
			for (int j = 0; j < getRealNumberColumnsBoard(); j++) {
				stringBuilder.append(getValue(i, j));
			}
			stringBuilder.append("\n");
		}
		return stringBuilder;
	}
	
	public void initializeBoard(CanvasCommand canvasCommand) {
		board = new String[canvasCommand.getHeight() + BoardConstants.CANTIDAD_CELDAS_ADICIONALES_DEFINICION_TABLERO][canvasCommand.getWidth() + BoardConstants.CANTIDAD_CELDAS_ADICIONALES_DEFINICION_TABLERO];
		commandsExecuted = new ArrayList<DrawingCommand>();
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				board[r][c] = " ";
			}
		}
	}
	
	public Collection<DrawingCommand> getRectangleCommandsExecuted() {
		Collection<DrawingCommand> subCollectionComandsExecuted = new ArrayList<DrawingCommand>();
		for (DrawingCommand drawingCommand : commandsExecuted) {
			if (drawingCommand instanceof RectangleCommand) {
				subCollectionComandsExecuted.add(drawingCommand);
			}
		}
		return subCollectionComandsExecuted;
	}
	
	public DrawingCommand getCanvasCommandExecuted() {
		for (DrawingCommand drawingCommand : commandsExecuted) {
			if (drawingCommand instanceof CanvasCommand) {
				return drawingCommand;
			}
		}
		return null;
	}
	
	private boolean validatePositionWithinPaintingArea(int r, int c) {
		return r <= getNumberRowsPaintingArea() && c <= getNumberColumnsPaintingArea();
	}
}
