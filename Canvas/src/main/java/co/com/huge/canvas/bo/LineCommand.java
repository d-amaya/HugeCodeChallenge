package co.com.huge.canvas.bo;

import java.util.regex.Matcher;

import co.com.huge.canvas.board.DrawingBoard;
import co.com.huge.canvas.board.constant.BoardConstants;
import co.com.huge.canvas.board.exception.DrawingBoardException;
import co.com.huge.canvas.factory.constant.FactoryConstants.TypeWritingPaintingCommand;

public class LineCommand extends DrawingCommand {

	private String command = null;
	private int x1 = 0;
	private int y1 = 0;
	private int x2 = 0;
	private int y2 = 0;
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	@Override
	public void setCoordinates(Matcher matcher, String command) {
		this.setCommand(command);
		this.setX1(Integer.parseInt(matcher.group(3)));
		this.setY1(Integer.parseInt(matcher.group(7)));
		this.setX2(Integer.parseInt(matcher.group(11)));
		this.setY2(Integer.parseInt(matcher.group(15)));
	}
	
	@Override
	public boolean validateCoordinates() {
		return isVerticalLine() || isHorizontalLine();
	}
	
	@Override
	public void draw(DrawingBoard drawingBoard) throws DrawingBoardException {
		if (isHorizontalLine()) {
			drawHorizontalLine(x1, x2, y1, drawingBoard, BoardConstants.CHARACTER_LINE_RECTANGLE);
		} else if (isVerticalLine()) {
			drawVerticalLine(x1, y1, y2, drawingBoard, BoardConstants.CHARACTER_LINE_RECTANGLE);
		} 
	}
	
	@Override
	public TypeWritingPaintingCommand getTypeWritingOnPaintingArea() {
		return TypeWritingPaintingCommand.TYPE_WRITING_PAINTING_AREA;
	}
	
	private boolean isHorizontalLine() {
		return y1 == y2 && ((x2 - x1) > 0);
	}
	
	private boolean isVerticalLine() {
		return x1 == x2 && ((y2 - y1) > 0);
	}
}
