
package utilities;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ControllerCreator {

	public static void main(final String[] args) throws Exception {
		//Uri de Archivo a crear
		String doc = "src/main/java/controllers/";
		//Uri de template a leer
		String templateFile;
		Scanner reader = new Scanner(System.in);
		//Pedir authority a usuario
		System.out.println("Enter authority name (leave blank if none)[First letter Upper case]:");
		String authority = reader.nextLine();
		//Pedir entidad a usuario
		System.out.println("Enter entity name[First letter Upper case]:");
		String entity = reader.nextLine();
		//Pedir entidad auxiliar a usuario
		System.out.println("Enter auxiliary entity name (leave blank if none)[All lower case]:");
		String auxiliary = reader.nextLine();
		reader.close();
		if (entity.length() == 0)
			throw new Exception("ERROR: Must choose an entity name.");
		//Es con authority o no?
		if (!authority.isEmpty()) {
			doc = doc.concat(authority + "/");
			templateFile = "src/main/java/utilities/internal/templateWithAuthority.txt";
			//Crear directorio si no existe
			new File(doc).mkdirs();
			doc = doc.concat(authority);
		} else {
			templateFile = "src/main/java/utilities/internal/template.txt";
			//Crear directorio si no existe
			new File(doc).mkdirs();
		}
		//Añadir nombre de archivo a crear a la uri
		doc = doc.concat(entity + "Controller.java");
		//Crear nombre de entidad para uso como atributo
		String subEntity = Character.toLowerCase(entity.charAt(0)) + entity.substring(1);
		//Crear printer
		PrintWriter printer = new PrintWriter(doc);
		//Leer template y convertir a string con codificación UTF-8
		String template = new String(Files.readAllBytes(Paths.get(templateFile)), "UTF-8");
		//Insertar nombres de entidad y entidad como atributo
		template = template.replace("%ent%", entity);
		template = template.replace("%ea%", subEntity);
		//Insertar nombre de autoridad si procede
		if (!authority.isEmpty()) {
			String subAuthority = Character.toLowerCase(authority.charAt(0)) + authority.substring(1);
			template = template.replace("%auth%", authority);
			template = template.replace("%autha%", subAuthority);
		}
		if (!auxiliary.isEmpty()) {
			template = template.replace("%aux%", "@RequestParam(required = true) final int " + auxiliary + "Id");
			template = template.replace("%aux2%", auxiliary + "Id");
		} else {
			template = template.replace("%aux%", "");
			template = template.replace("%aux2%", "");
		}

		//Imprimir a archivo y cerrar printer
		printer.print(template);
		printer.close();
	}
}
