package utilities.internal;

import java.io.File;

public class ConvertersList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File[] files = new File("src/main/java/converters").listFiles();
		for(File file: files)
			if(file.isFile()){
				String fileName = file.getName();
				int pos = fileName.lastIndexOf(".");
				String name = fileName.substring(0,pos);
				System.out.println("<bean class=\"converters."+name+"\"/>");
			}
	}

}
