package co.com.huge.canvas.bo;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;

import co.com.huge.canvas.board.DrawingBoard;
import co.com.huge.canvas.board.constant.BoardConstants;
import co.com.huge.canvas.board.exception.DrawingBoardException;
import co.com.huge.canvas.factory.constant.FactoryConstants.TypeWritingPaintingCommand;

public class FillCommand extends DrawingCommand {

	private Set<String> positions = new HashSet<String>();
	private String command = null;
	private int x = 0;
	private int y = 0;
	private String colour = null;
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	@Override
	public void setCoordinates(Matcher matcher, String command) {
		this.setCommand(command);
		this.setX(Integer.parseInt(matcher.group(3)));
		this.setY(Integer.parseInt(matcher.group(7)));
		this.setColour(matcher.group(11));
	}
	
	@Override
	public boolean validateCoordinates() {
		return x > 0 && y > 0;
	}
	
	@Override
	public void draw(DrawingBoard drawingBoard) throws DrawingBoardException {
		identifyPositions(x, y, drawingBoard);
		for (String position : positions) {
			int r = Integer.parseInt(position.split(BoardConstants.CHARACTER_EMPTY)[0]);
			int c = Integer.parseInt(position.split(BoardConstants.CHARACTER_EMPTY)[1]);
			drawingBoard.setValue(r, c, colour, getTypeWritingOnPaintingArea());
		}
	}
	
	private void identifyPositions(int x, int y, DrawingBoard drawingBoard) {
		String position = String.valueOf(y).concat(BoardConstants.CHARACTER_EMPTY).concat(String.valueOf(x));
		if (x > 0 && y > 0 && x <= drawingBoard.getNumberColumnsPaintingArea() && y <= drawingBoard.getNumberRowsPaintingArea()) {
			if (!positions.contains(position) && (BoardConstants.CHARACTER_EMPTY.equals(drawingBoard.getValue(y, x)) || (!BoardConstants.CHARACTER_LINE_RECTANGLE.equals(drawingBoard.getValue(y, x))))) {
				positions.add(position);
				identifyPositions(x, y-1, drawingBoard);
				identifyPositions(x, y+1, drawingBoard);
				identifyPositions(x+1, y, drawingBoard);
				identifyPositions(x-1, y, drawingBoard);
			}
		}
	}
	
	@Override
	public TypeWritingPaintingCommand getTypeWritingOnPaintingArea() {
		return TypeWritingPaintingCommand.TYPE_WRITING_PAINTING_AREA;
	}
}
