package co.com.huge.canvas.factory;

import co.com.huge.canvas.bo.DrawingCommand;
import co.com.huge.canvas.factory.constant.FactoryConstants.RegexDrawingCommand;
import co.com.huge.canvas.factory.exception.DrawingCommandFactoryException;
import co.com.huge.canvas.factory.exception.InvalidCoordinateException;
import co.com.huge.canvas.factory.exception.UnknownDrawingCommandException;

public class DrawingCommandFactory {
	
	public static DrawingCommand getDrawingCommand(String command) throws UnknownDrawingCommandException, DrawingCommandFactoryException, InvalidCoordinateException {
		RegexDrawingCommand regexDrawingCommand = null;
		DrawingCommand drawingCommand = null;
		try {
			regexDrawingCommand = RegexDrawingCommand.getDrawingCommand(command);
			if (regexDrawingCommand != null) {
				drawingCommand = regexDrawingCommand.getClassDrawingCommand().newInstance();
				drawingCommand.setCoordinatesFromDrawingCommand(regexDrawingCommand, command);
			} else {
				throw new UnknownDrawingCommandException("The drawing command typed is not supported.");
			}
			return drawingCommand;
		} catch (IllegalAccessException e) {
			throw new DrawingCommandFactoryException("Ocurred an error creating the requested DrawingCommand.", e);
		} catch (InstantiationException e) {
			throw new DrawingCommandFactoryException("Ocurred an error creating the requested DrawingCommand.", e);
		}  
	}
}
