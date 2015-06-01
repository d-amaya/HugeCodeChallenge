package co.com.huge.canvas.board;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import co.com.huge.canvas.CanvasBusinessException;
import co.com.huge.canvas.bo.CanvasCommand;
import co.com.huge.canvas.bo.DrawingCommand;
import co.com.huge.canvas.board.exception.DrawingBoardException;
import co.com.huge.canvas.factory.DrawingCommandFactory;

public class DrawingBoardTest {

	private static final String LINEA_HORIZONTAL_CONVAS = "----------------------";
	private static final String LINEA_VERTICAL_CANVAS = "|                    |";
	private static final String SALTO_LINEA = "\n";
	
	private static DrawingBoard drawingBoard = null;
	
	@Before
	public void initializeCanvasOnDrawingBoard() throws CanvasBusinessException {
		CanvasCommand canvasCommand = (CanvasCommand) DrawingCommandFactory.getDrawingCommand("C 20 4");
		drawingBoard = new DrawingBoard(canvasCommand);
		drawingBoard.drawCommandOnBoard(canvasCommand);
		Assert.assertNotNull(drawingBoard);
		Assert.assertEquals(initializeCanvasMock().toString(), drawingBoard.convertBoard2StringBuilder().toString());
	}
	
	@Test
	public void drawHorizontalLineCommandOnBoard() throws CanvasBusinessException {
		DrawingCommand lineCommand = DrawingCommandFactory.getDrawingCommand("L 1 2 6 2");
		drawingBoard.drawCommandOnBoard(lineCommand);
		Assert.assertEquals("|xxxxxx              |", convertRow2String(2));
	}
	
	@Test
	public void drawVerticalLineCommandOnBoard() throws CanvasBusinessException {
		DrawingCommand lineCommand = DrawingCommandFactory.getDrawingCommand("L 6 3 6 4");
		drawingBoard.drawCommandOnBoard(lineCommand);
		Assert.assertEquals("-  xx-", convertColumn2String(6));
	}
	
	@Test
	public void drawRectangleCommandOnBoard() throws CanvasBusinessException {
		DrawingCommand rectangleCommand = DrawingCommandFactory.getDrawingCommand("R 16 1 20 3");
		drawingBoard.drawCommandOnBoard(rectangleCommand);
		Assert.assertEquals("|               xxxxx|", convertRow2String(1));
		Assert.assertEquals("|               x   x|", convertRow2String(2));
		Assert.assertEquals("|               xxxxx|", convertRow2String(3));
	}
	
	@Test
	public void fillRectangleCommandOnBoard() throws CanvasBusinessException {
		DrawingCommand command = DrawingCommandFactory.getDrawingCommand("R 16 1 20 3");
		drawingBoard.drawCommandOnBoard(command);
		command = DrawingCommandFactory.getDrawingCommand("B 17 2 r");
		drawingBoard.drawCommandOnBoard(command);
		Assert.assertEquals("|               xxxxx|", convertRow2String(1));
		Assert.assertEquals("|               xrrrx|", convertRow2String(2));
		Assert.assertEquals("|               xxxxx|", convertRow2String(3));
	}
	
	@Test
	public void fillCanvasCommandOnBoard() throws CanvasBusinessException {
		DrawingCommand command = DrawingCommandFactory.getDrawingCommand("R 16 1 20 3");
		drawingBoard.drawCommandOnBoard(command);
		command = DrawingCommandFactory.getDrawingCommand("B 3 3 r");
		drawingBoard.drawCommandOnBoard(command);
		Assert.assertEquals("----------------------", convertRow2String(0));
		Assert.assertEquals("|rrrrrrrrrrrrrrrxxxxx|", convertRow2String(1));
		Assert.assertEquals("|rrrrrrrrrrrrrrrx   x|", convertRow2String(2));
		Assert.assertEquals("|rrrrrrrrrrrrrrrxxxxx|", convertRow2String(3));
		Assert.assertEquals("|rrrrrrrrrrrrrrrrrrrr|", convertRow2String(4));
		Assert.assertEquals("----------------------", convertRow2String(5));
	}
	
	@Test(expected=DrawingBoardException.class)
	public void drawingLineCommandOutOfPaintingAreaBottomEdge() throws CanvasBusinessException {
		DrawingCommand lineCommand = DrawingCommandFactory.getDrawingCommand("L 10 2 10 7");
		drawingBoard.drawCommandOnBoard(lineCommand);
	}
	
	@Test(expected=DrawingBoardException.class)
	public void drawingRectangleCommandOutOfPaintingArea() throws CanvasBusinessException {
		DrawingCommand rectangleCommand = DrawingCommandFactory.getDrawingCommand("R 16 2 25 4");
		drawingBoard.drawCommandOnBoard(rectangleCommand);
	}
	
	private static StringBuilder initializeCanvasMock() {
		StringBuilder stringBuilder = new StringBuilder(LINEA_HORIZONTAL_CONVAS);
		stringBuilder.append(SALTO_LINEA);
		stringBuilder.append(LINEA_VERTICAL_CANVAS);
		stringBuilder.append(SALTO_LINEA);
		stringBuilder.append(LINEA_VERTICAL_CANVAS);
		stringBuilder.append(SALTO_LINEA);
		stringBuilder.append(LINEA_VERTICAL_CANVAS);
		stringBuilder.append(SALTO_LINEA);
		stringBuilder.append(LINEA_VERTICAL_CANVAS);
		stringBuilder.append(SALTO_LINEA);
		stringBuilder.append(LINEA_HORIZONTAL_CONVAS);
		stringBuilder.append(SALTO_LINEA);
		return stringBuilder;
	}
	
	private String convertRow2String(int row) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int c = 0; c < drawingBoard.getRealNumberColumnsBoard(); c++) {
			stringBuilder.append(drawingBoard.getValue(row, c));
		}
		return stringBuilder.toString();
	}
	
	private String convertColumn2String(int column) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int r = 0; r < drawingBoard.getRealNumberRowsBoard(); r++) {
			stringBuilder.append(drawingBoard.getValue(r, column));
		}
		return stringBuilder.toString();
	}
	
}
