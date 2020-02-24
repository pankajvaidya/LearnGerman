package home;

import java.util.ArrayList;
import java.util.List;

public class SplitTokens {
	
	public static void main (String args[]) {
		List<String> tokens = new ArrayList<String>();
		tokens.add("der");
		tokens.add("das");
		tokens.add("die");
		
		String passage = "Nomen (auch Substantiv, Dingwort, Hauptwort oder Namenwort genannt) bezeichnen Dinge (der Löffel), "
				+ "Lebewesen (die Frau) und Abstrakta (das Wetter). Im Deutschen werden Nomen großgeschrieben und wir müssen Nomen deklinieren.";

		for (String article : tokens) {
			passage = passage.replaceAll("\\b" + article + "\\b", "---");
		}
		System.out.println(passage);
	}
	

}
