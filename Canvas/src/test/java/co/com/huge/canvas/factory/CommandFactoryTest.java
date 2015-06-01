package co.com.huge.canvas.factory;

import org.junit.Assert;
import org.junit.Test;

import co.com.huge.canvas.CanvasBusinessException;
import co.com.huge.canvas.bo.CanvasCommand;
import co.com.huge.canvas.bo.DrawingCommand;
import co.com.huge.canvas.bo.FillCommand;
import co.com.huge.canvas.bo.LineCommand;
import co.com.huge.canvas.bo.RectangleCommand;
import co.com.huge.canvas.factory.exception.InvalidCoordinateException;
import co.com.huge.canvas.factory.exception.UnknownDrawingCommandException;

public class CommandFactoryTest {

	@Test
	public void createInstanceForCanvasCommand() throws CanvasBusinessException {
		String commandCanvas = "C 20 4";
		DrawingCommand drawingCommand = DrawingCommandFactory.getDrawingCommand(commandCanvas);
		Assert.assertTrue("", drawingCommand instanceof CanvasCommand);
		Assert.assertEquals(commandCanvas, ((CanvasCommand)drawingCommand).getCommand());
		Assert.assertEquals(20, ((CanvasCommand)drawingCommand).getWidth());
		Assert.assertEquals(4, ((CanvasCommand)drawingCommand).getHeight());
	}
	
	@Test
	public void createInstanceForLineCommand() throws CanvasBusinessException {
		String commandLine = "L 1 2 6 2";
		DrawingCommand drawingCommand = DrawingCommandFactory.getDrawingCommand(commandLine);
		Assert.assertTrue("", drawingCommand instanceof LineCommand);
		Assert.assertEquals(commandLine, ((LineCommand)drawingCommand).getCommand());
		Assert.assertEquals(1, ((LineCommand)drawingCommand).getX1());
		Assert.assertEquals(2, ((LineCommand)drawingCommand).getY1());
		Assert.assertEquals(6, ((LineCommand)drawingCommand).getX2());
		Assert.assertEquals(2, ((LineCommand)drawingCommand).getY2());
	}
	
	@Test
	public void createInstanceForRectangleCommand() throws CanvasBusinessException {
		String commandRectangle = "R 16 1 20 3";
		DrawingCommand drawingCommand = DrawingCommandFactory.getDrawingCommand(commandRectangle);
		Assert.assertTrue("", drawingCommand instanceof RectangleCommand);
		Assert.assertEquals(commandRectangle, ((RectangleCommand)drawingCommand).getCommand());
		Assert.assertEquals(16, ((RectangleCommand)drawingCommand).getX1());
		Assert.assertEquals(1, ((RectangleCommand)drawingCommand).getY1());
		Assert.assertEquals(20, ((RectangleCommand)drawingCommand).getX2());
		Assert.assertEquals(3, ((RectangleCommand)drawingCommand).getY2());
	}
	
	@Test
	public void createInstanceForFillCommand() throws CanvasBusinessException {
		String commandFill = "B 10 3 o";
		DrawingCommand drawingCommand = DrawingCommandFactory.getDrawingCommand(commandFill);
		Assert.assertTrue("", drawingCommand instanceof FillCommand);
		Assert.assertEquals(commandFill, ((FillCommand)drawingCommand).getCommand());
		Assert.assertEquals(10, ((FillCommand)drawingCommand).getX());
		Assert.assertEquals(3, ((FillCommand)drawingCommand).getY());
		Assert.assertEquals("o", ((FillCommand)drawingCommand).getColour());
	}
	
	@Test(expected = UnknownDrawingCommandException.class)
	public void unknownDrawingCommand() throws CanvasBusinessException {
		DrawingCommandFactory.getDrawingCommand("U 8 12 34 23");
	}
	
	@Test(expected=UnknownDrawingCommandException.class)
	public void createCommandOutOfCoordinateTopZero() throws CanvasBusinessException {
		DrawingCommandFactory.getDrawingCommand("L 10 0 10 4");
	}
	
	@Test(expected=UnknownDrawingCommandException.class)
	public void createCommandOutOfCoordinateLeftZero() throws CanvasBusinessException {
		DrawingCommandFactory.getDrawingCommand("L 0 2 15 2");
	}
	
	@Test(expected=UnknownDrawingCommandException.class)
	public void createCommandOutOfCoordinateTopNegativeNumber() throws CanvasBusinessException {
		DrawingCommandFactory.getDrawingCommand("L 10 -10 10 4");
	}
	
	@Test(expected=InvalidCoordinateException.class)
	public void createDrawingCanvasWithEnoughAreaToPaintTheMinimumLine() throws CanvasBusinessException {
		DrawingCommandFactory.getDrawingCommand("C 1 1");
	}
	
	@Test(expected=InvalidCoordinateException.class)
	public void createDrawingRectangleWithInvalidCoordinates1() throws CanvasBusinessException {
		DrawingCommandFactory.getDrawingCommand("R 3 16 1 20");
	}
	
	@Test(expected=InvalidCoordinateException.class)
	public void createDrawingRectangleWithInvalidCoordinates2() throws CanvasBusinessException {
		DrawingCommandFactory.getDrawingCommand("R 16 1 17 2");
	}
	
	@Test(expected=InvalidCoordinateException.class)
	public void createDrawingLineWithPointCoordinates() throws CanvasBusinessException {
		DrawingCommandFactory.getDrawingCommand("L 2 2 2 2");
	}
	
	@Test(expected=InvalidCoordinateException.class)
	public void createDrawingLineWithDiagonalCoordinates() throws CanvasBusinessException {
		DrawingCommandFactory.getDrawingCommand("L 2 2 8 3");
	}
}
