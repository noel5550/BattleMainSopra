import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Properties;

import javax.swing.text.html.Option;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mkyong.client.JCliGet;

public class BattleMain {
	/**
	 * 
	 * @param args ceci est ce qu'on met dans les config du Run
	 */
	public static void main(String[] args) throws ParseException, IOException {
//		final Options options = new Options();
//		options.addOption("p", false, "afficher"); //ceci est pour l'option 0 "-p"
//		options.addOption("config", false, "config"); //ceci est pour l'option 1 "config"
//		
//		//Parser les commandes dans le config (récupérer ce qui est dans le config du Run)
//		final CommandLineParser parser = new DefaultParser();
//		final CommandLine cmd = parser.parse(options, args);
//		
//		if(cmd.hasOption("p")) {
//			System.out.println("pong");
//		}
//		if(cmd.hasOption("config")) {
//			
//			//Tout ca c'est pour lire le contenu du fichier "configuration.properties"
//			Properties prop = new Properties();
//			InputStream input = BattleMain.class.getClassLoader().getResourceAsStream("configuration.properties");
//			if(input != null) {
//				//mettre dans prop les contenu du fichier config
//				prop.load(input);
//				//lire le contenu du prop avec les valeurs de "team.name" et "team.password"
//				System.out.println(prop.getProperty("team.name"));
//				System.out.println(prop.getProperty("team.password"));
//			}else {
//				//si fichier n'existe pas
//				throw new FileNotFoundException();
//			}
//			//fermer fichier
//			input.close();
//		}
		
		JCliGet jcg = new JCliGet();
		//jcg.affrontement("c8caa067-0ab6-4bde-a6ce-d4942ea07c95");
		//jcg.affrontementBot(6,"c8caa067-0ab6-4bde-a6ce-d4942ea07c95");
		//jcg.plateau("5");
		jcg.partie("5" , "c8caa067-0ab6-4bde-a6ce-d4942ea07c95");
	}
	
	

}

