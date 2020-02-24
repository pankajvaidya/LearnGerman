package home;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;


public class Test123 {

	public static void main(String[] args) {

		String masterString = "Dieser Artikel befasst sich mit dem Nomen. Es wird erklärt, was man unter einem Nomen versteht "
				+ "und es wird auf die Artikel, das Genus, den Numerus, die vier Kasus (Fälle) sowie die verschiedenen Deklinationen "
				+ "eingegangen. Zum besseren Verständnis dienen Beispiele mit Erklärungen. Außerdem haben wir noch Übungen rund ums Nomen "
				+ "für euch. Dieser Artikel gehört zu unserem Bereich Deutsch." ;
		
		String patternString = "\\b(der|das|die|den|dem|des)\\b";
		
		List<String> tempList = new ArrayList<String>();
		tempList.add("dem"); tempList.add("des"); tempList.add("das"); tempList.add("den"); tempList.add("die"); tempList.add("die");
		
		String evaluationResultString  = "" ;

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(masterString);

        int count = 0;
        int endIndexToTrack = 0 ;
        while(matcher.find()) {
            count++;
            System.out.println("found: " + count + " : "
                    + matcher.start() + " - " + matcher.end() + " group: " + matcher.group());
            String valueToCompare  = tempList.get(count-1);
            evaluationResultString += masterString.substring(endIndexToTrack, matcher.start());
            endIndexToTrack = matcher.end();
             
            if (matcher.group().equals(valueToCompare)) {
            	evaluationResultString += "<font color=green>" + valueToCompare + "</font>" ;
            } else {
            	evaluationResultString += "<font color=red>" + valueToCompare + "</font>" ;
            }
        }
        System.out.println(masterString);
        System.out.println(evaluationResultString);
	}

}
