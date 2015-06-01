package co.com.huge.canvas.bo;

import java.util.regex.Matcher;

import co.com.huge.canvas.board.DrawingBoard;
import co.com.huge.canvas.board.constant.BoardConstants;
import co.com.huge.canvas.board.exception.DrawingBoardException;
import co.com.huge.canvas.factory.constant.FactoryConstants.TypeWritingPaintingCommand;

public class CanvasCommand extends DrawingCommand {

	private String command = null;
	private int width = 0;
	private int height = 0;
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public void setCoordinates(Matcher matcher, String command) {
		this.setCommand(command);
		this.setWidth(Integer.parseInt(matcher.group(3)));
		this.setHeight(Integer.parseInt(matcher.group(7)));
	}
	
	@Override
	public boolean validateCoordinates() {
		return (width >= 2 && height >= 1) || (width >= 1 && height >= 2);
	}

	@Override
	public void draw(DrawingBoard drawingBoard) throws DrawingBoardException {
		drawingBoard.initializeBoard(this);
		drawHorizontalLine(0, drawingBoard.getRealNumberColumnsBoard()-1, 0, drawingBoard, BoardConstants.CHARACTER_LINE_CANVAS_HORIZONTAL);
		drawHorizontalLine(0, drawingBoard.getRealNumberColumnsBoard()-1, drawingBoard.getRealNumberRowsBoard()-1, drawingBoard, BoardConstants.CHARACTER_LINE_CANVAS_HORIZONTAL);
		drawVerticalLine(0, 1, height, drawingBoard, BoardConstants.CHARACTER_LINE_CANVAS_VERTICAL);
		drawVerticalLine(drawingBoard.getRealNumberColumnsBoard()-1, 1, height, drawingBoard, BoardConstants.CHARACTER_LINE_CANVAS_VERTICAL);
	}

	@Override
	public TypeWritingPaintingCommand getTypeWritingOnPaintingArea() {
		return TypeWritingPaintingCommand.TYPE_WRITING_TOTAL_AREA;
	}
}