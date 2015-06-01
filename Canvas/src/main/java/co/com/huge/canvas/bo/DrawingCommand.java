package co.com.huge.canvas.bo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.com.huge.canvas.board.DrawingBoard;
import co.com.huge.canvas.board.exception.DrawingBoardException;
import co.com.huge.canvas.factory.constant.FactoryConstants.RegexDrawingCommand;
import co.com.huge.canvas.factory.constant.FactoryConstants.TypeWritingPaintingCommand;
import co.com.huge.canvas.factory.exception.InvalidCoordinateException;

public abstract class DrawingCommand {

	public abstract void setCoordinates(Matcher matcher, String comand);
	public abstract boolean validateCoordinates();
	public abstract TypeWritingPaintingCommand getTypeWritingOnPaintingArea();
	public abstract void draw(DrawingBoard drawingBoard) throws DrawingBoardException;
	
	public void setCoordinatesFromDrawingCommand(RegexDrawingCommand regexDrawingCommand, String command) throws InvalidCoordinateException {
		Pattern pattern = Pattern.compile(regexDrawingCommand.getRegexCommand());
		Matcher matcher = pattern.matcher(command);
		matcher.matches();
		setCoordinates(matcher, command);
		if(!validateCoordinates()) {
			throw new InvalidCoordinateException("The coordinates to create the required command are invalid.");
		}
	}

	public void drawCommand(DrawingBoard drawingBoard) throws DrawingBoardException {
		draw(drawingBoard);
		drawingBoard.printDrawingBoard();
	}
	
	public void drawHorizontalLine(int c1, int c2, int r, DrawingBoard drawingBoard, String value) throws DrawingBoardException {
		for (int c = c1; c <= c2; c++) {
			drawingBoard.setValue(r, c, value, getTypeWritingOnPaintingArea());
		}
	}
	
	public void drawVerticalLine(int c, int r1, int r2, DrawingBoard drawingBoard, String value) throws DrawingBoardException {
		for (int r = r1; r <= r2; r++) {
			drawingBoard.setValue(r, c, value, getTypeWritingOnPaintingArea());
		}
	}
}
