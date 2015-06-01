package co.com.huge.canvas.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import co.com.huge.canvas.CanvasBusinessException;
import co.com.huge.canvas.bo.CanvasCommand;
import co.com.huge.canvas.bo.DrawingCommand;
import co.com.huge.canvas.board.DrawingBoard;
import co.com.huge.canvas.factory.DrawingCommandFactory;

public class DrawingCommandExecution {

	private static final String QUIT_APPLICATION_COMMAND = "Q";
	private static BufferedReader bufferedReader = null;
	
	public static void main(String[] args) throws IOException {
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String command = getConsoleInput("Please type a Canvas command: ");
        
        CanvasCommand canvasCommand = getCanvasCommand(command);
        DrawingBoard drawingBoard = new DrawingBoard(canvasCommand);
        executeCommandOnBoard(drawingBoard, canvasCommand);
        
        DrawingCommand drawingCommand = null;
        command = getConsoleInput("Please type a Line, Rectangle, Fill or Quit command: ");
        do {
        	if (QUIT_APPLICATION_COMMAND.equalsIgnoreCase(command)) {
        		finishProgramExecution();
        	}
            drawingCommand = getDrawingCommand(command);
            executeCommandOnBoard(drawingBoard, drawingCommand);
            command = getConsoleInput("Please type a Line, Rectangle, Fill or Quit command: ");
        } while(!QUIT_APPLICATION_COMMAND.equalsIgnoreCase(command));
	}

	private static void executeCommandOnBoard(DrawingBoard drawingBoard, DrawingCommand drawingCommand) {
		try {
			drawingBoard.drawCommandOnBoard(drawingCommand);
		} catch (CanvasBusinessException ce) {
			String command = getConsoleInput(ce.getMessage() + "\nPlease type the command again or 'Q' to quit: ");
			executeCommandOnBoard(drawingBoard, getDrawingCommand(command));
		} catch (Exception e) {
			e.printStackTrace();
			finishProgramExecution();
		}
	}
	
	private static CanvasCommand getCanvasCommand(String command) {
		DrawingCommand drawingCommand = null;
		try {
			drawingCommand = getDrawingCommand(command);
        	if (!(drawingCommand instanceof CanvasCommand)) {
        		throw new CanvasBusinessException("The typed command is not a Canvas command.");
        	}
		} catch (CanvasBusinessException e) {
			while(drawingCommand == null || !(drawingCommand instanceof CanvasCommand)) {
				command = getConsoleInput("The command typed is not a Canvas command. Please type a Canvas command to start: ");
        		drawingCommand = getDrawingCommand(command);
        	}
		}
		return (CanvasCommand)drawingCommand;
	}
	
	private static DrawingCommand getDrawingCommand(String command) {
		DrawingCommand drawingCommand = null;
		while(!QUIT_APPLICATION_COMMAND.equalsIgnoreCase(command) && drawingCommand == null) {
			try {
				drawingCommand = DrawingCommandFactory.getDrawingCommand(command);
			} catch (CanvasBusinessException e) {
				command = getConsoleInput(e.getMessage() + "\nPlease type the command again or 'Q' to quit: ");
			}
		}
		if (QUIT_APPLICATION_COMMAND.equalsIgnoreCase(command)) {
			finishProgramExecution();
		}
		return drawingCommand;
	}
	
	private static String getConsoleInput(String text) {
		String command = "";
		try {
			System.out.println(text);
			command = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			finishProgramExecution();
		}
		return command;
	}
	
	private static void finishProgramExecution() {
		System.exit(0);
	}
	
}
