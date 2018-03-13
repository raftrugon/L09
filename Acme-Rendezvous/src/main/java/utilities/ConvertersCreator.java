package utilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ConvertersCreator {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter entity name[First letter Upper case]:");
		String entity = reader.nextLine();
		reader.close();
		//Converters
		String converter1 = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/converter1Template.txt")), "UTF-8");
		String converter2 = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/converter2Template.txt")), "UTF-8");
		String fileConverter1 = "src/main/java/converters/"+entity+"ToStringConverter.java";
		String fileConverter2 = "src/main/java/converters/StringTo"+entity+"Converter.java";
		PrintWriter printer1 = new PrintWriter(fileConverter1);
		PrintWriter printer2 = new PrintWriter(fileConverter2);
		converter1 = converter1.replace("%ent%", entity);
		converter1 = converter1.replace("%ea%", firstLower(entity));
		converter2 = converter2.replace("%ent%", entity);
		converter2 = converter2.replace("%ea%", firstLower(entity));
		printer1.print(converter1);
		printer2.print(converter2);
		printer1.close();
		printer2.close();
	}
	
	private static String firstLower(String input){
		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}

}
