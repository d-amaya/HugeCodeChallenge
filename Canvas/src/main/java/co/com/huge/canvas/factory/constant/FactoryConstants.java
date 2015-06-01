package co.com.huge.canvas.factory.constant;

import java.util.regex.Pattern;

import co.com.huge.canvas.bo.CanvasCommand;
import co.com.huge.canvas.bo.DrawingCommand;
import co.com.huge.canvas.bo.FillCommand;
import co.com.huge.canvas.bo.LineCommand;
import co.com.huge.canvas.bo.RectangleCommand;

public class FactoryConstants {

	public enum TypeWritingPaintingCommand {
		TYPE_WRITING_TOTAL_AREA, TYPE_WRITING_PAINTING_AREA;
		
		private TypeWritingPaintingCommand() {
			
		}
	}
	
	public enum RegexDrawingCommand {
		REGEX_CANVAS_COMMAND("^(C)( )(([1-9]{1})([0-9]{1,})?)( )(([1-9]{1})([0-9]{1,})?)$", CanvasCommand.class),
		REGEX_LINE_COMMAND("^(L)( )(([1-9]{1})([0-9]{1,})?)( )(([1-9]{1})([0-9]{1,})?)( )(([1-9]{1})([0-9]{1,})?)( )(([1-9]{1})([0-9]{1,})?)$", LineCommand.class),
		REGEX_RECTANGLE_COMMAND("^(R)( )(([1-9]{1})([0-9]{1,})?)( )(([1-9]{1})([0-9]{1,})?)( )(([1-9]{1})([0-9]{1,})?)( )(([1-9]{1})([0-9]{1,})?)$", RectangleCommand.class),
		REGEX_FILL_COMMAND("^(B)( )(([1-9]{1})([0-9]{1,})?)( )(([1-9]{1})([0-9]{1,})?)( )(([0-9a-wy-zA-WY-Z]{1}))$", FillCommand.class);
		
		private String regexCommand;
		private Class<? extends DrawingCommand> classDrawingCommand;
		
		private RegexDrawingCommand(String regexCommand, Class<? extends DrawingCommand> classDrawingCommand) {
			this.regexCommand = regexCommand;
			this.classDrawingCommand = classDrawingCommand;
		}
		
		public String getRegexCommand() {
			return regexCommand;
		}
		
		public Class<? extends DrawingCommand> getClassDrawingCommand() {
			return classDrawingCommand;
		}
		
		public static RegexDrawingCommand getDrawingCommand(String command) {
			for (RegexDrawingCommand regexDrawingCommand : RegexDrawingCommand.values()) {
				Pattern patternDrawingCommand = Pattern.compile(regexDrawingCommand.getRegexCommand());
				if (patternDrawingCommand.matcher(command).matches()) {
					return regexDrawingCommand;
				}
			}
			return null;
		}
	}
}
