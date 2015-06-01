package co.com.huge.canvas.bo;

import java.util.Collection;
import java.util.regex.Matcher;

import co.com.huge.canvas.board.DrawingBoard;
import co.com.huge.canvas.board.constant.BoardConstants;
import co.com.huge.canvas.board.exception.DrawingBoardException;
import co.com.huge.canvas.factory.constant.FactoryConstants.TypeWritingPaintingCommand;

public class FillCommand extends DrawingCommand {

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
		Collection<DrawingCommand> rectangleCommands = drawingBoard.getRectangleCommandsExecuted();
		DrawingCommand drawingCommand = identifyRectangleForGivenCoordinates(rectangleCommands);
		if (drawingCommand != null) {
			fillRectangleAreaWithColour(drawingBoard, drawingCommand, colour);
		} else {
			fillCanvasWithoutRectangle(drawingBoard, rectangleCommands);
		}
	}
	
	@Override
	public TypeWritingPaintingCommand getTypeWritingOnPaintingArea() {
		return TypeWritingPaintingCommand.TYPE_WRITING_TOTAL_AREA;
	}
	
	private DrawingCommand identifyRectangleForGivenCoordinates(Collection<DrawingCommand> rectangleCommands) {
		for (DrawingCommand rectangleCommand : rectangleCommands) {
			if (isCoordinateWithinRectangleArea(rectangleCommand)) {
				return rectangleCommand;
			}
		}
		return null;
	}
	
	private boolean isCoordinateWithinRectangleArea(DrawingCommand drawingCommand) {
		RectangleCommand rectangleCommand = (RectangleCommand) drawingCommand;
		return x >= rectangleCommand.getX1() && x <= rectangleCommand.getX2() && y >= rectangleCommand.getY1() && y <= rectangleCommand.getY2();
	}
	
	private void fillCanvasAreaWithColour(DrawingBoard drawingBoard, DrawingCommand drawingCommand) throws DrawingBoardException {
		CanvasCommand canvasCommand = (CanvasCommand) drawingCommand; 
		for (int r = 1; r <= canvasCommand.getHeight(); r++) {
			for (int c = 1; c <= canvasCommand.getWidth(); c++) {
				String value = drawingBoard.getValue(r, c);
				if (BoardConstants.CHARACTER_EMPTY.equals(value)) {
					drawingBoard.setValue(r, c, colour, getTypeWritingOnPaintingArea());
				}
			}
		}
	}
	
	private void fillCanvasWithoutRectangle(DrawingBoard drawingBoard, Collection<DrawingCommand> rectangleCommands) throws DrawingBoardException {
		for (DrawingCommand rectangleCommand : rectangleCommands) {
			fillRectangleAreaWithColour(drawingBoard, rectangleCommand, "%");
		}
		fillCanvasAreaWithColour(drawingBoard, drawingBoard.getCanvasCommandExecuted());
		for (DrawingCommand rectangleCommand : rectangleCommands) {
			resetBonusCharacterWithinRectangle(drawingBoard, rectangleCommand);
		}
	}
	
	private void resetBonusCharacterWithinRectangle(DrawingBoard drawingBoard, DrawingCommand drawingCommand) throws DrawingBoardException  {
		RectangleCommand rectangleCommand = (RectangleCommand) drawingCommand;
		for (int r = (rectangleCommand.getY1() + 1); r < rectangleCommand.getY2(); r++) {
			for (int c = (rectangleCommand.getX1() + 1); c < rectangleCommand.getX2(); c++) {
				if (BoardConstants.BONUS_FILL.equals(drawingBoard.getValue(r, c))) {
					drawingBoard.setValue(r, c, BoardConstants.CHARACTER_EMPTY, getTypeWritingOnPaintingArea());
				}
			}
		}
	}
	
	private void fillRectangleAreaWithColour(DrawingBoard drawingBoard, DrawingCommand drawingCommand, String colour) throws DrawingBoardException {
		RectangleCommand rectangleCommand = (RectangleCommand) drawingCommand;
		for (int r = (rectangleCommand.getY1() + 1); r < rectangleCommand.getY2(); r++) {
			for (int c = (rectangleCommand.getX1() + 1); c < rectangleCommand.getX2(); c++) {
				if (!BoardConstants.BONUS_FILL.equals(colour) || BoardConstants.CHARACTER_EMPTY.equals(drawingBoard.getValue(r, c))
						|| (BoardConstants.CHARACTER_EMPTY.equals(colour) && BoardConstants.BONUS_FILL.equals(drawingBoard.getValue(r, c)))) {
					drawingBoard.setValue(r, c, colour, getTypeWritingOnPaintingArea());
				}
			}
		}
	}
	
}
