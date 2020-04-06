package home;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

@Controller
public class HomeController {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Inject
	LearnGermanDAO learnGermanDao;

	@RequestMapping("/")
		public String index() {
			return "index";
	}
	   
    @RequestMapping("/selectLanguageComponent")
    public String home(ModelMap modelMap) {
    	List<LanguageComponentVO> languageComponentList = learnGermanDao.getLanguageComponentList();
    	modelMap.addAttribute("languageComponentList", languageComponentList);
        return "selectLanguageComponent";
    }

    @RequestMapping("/blankOutLangageComponent")
    public String blankOutLangageComponent(@RequestParam("languageText") String languageText,
    		@RequestParam("languageComponent") String languageComponent, 
    		ModelMap modelMap) {
    	System.out.println("languageComponent :" + languageComponent);
    	System.out.println("languageText :" + languageText);
    	
    	//languageText = StringEscapeUtils.escapeHtml4(languageText);

    	modelMap.addAttribute("languageComponent", languageComponent);
    	modelMap.addAttribute("languageText", languageText);
    	
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("language_component_id", languageComponent);
    	List<String> languageBrick = learnGermanDao.getWordList(params);
    	
    	String modifiedLanguageText = splitLanguageBricks(languageText,languageBrick);
    	modelMap.addAttribute("modifiedLanguageText", modifiedLanguageText);

    	System.out.println(languageBrick);
    	System.out.println("Size is : " + languageBrick.size());
    	System.out.println("done");
    	
    	if (languageText.equals(modifiedLanguageText)) {
        	Map<String,Object> paramsLanguageComponent = new HashMap<String,Object>();
        	paramsLanguageComponent.put("language_component_id", languageComponent);
        	LanguageComponentVO languageComponentVO = learnGermanDao.getLanguageComponent(paramsLanguageComponent);
        	modelMap.addAttribute("languageComponent", languageComponentVO);
    		return "languageComponentNotFound" ;
    	}
        return "blankOutLangageComponent";
    }    

    @RequestMapping("/evaluateLangageComponent")
    public String evaluateLangageComponent(
    		@RequestParam("languageText") String languageText,
    		@RequestParam("modifiedLanguageText") String modifiedLanguageText,
    		@RequestParam("languageComponent") String languageComponent,
    		@RequestParam("languageBrick") List<String> languageBricks,
    		ModelMap modelMap) {
    	
    	// If languageBricks array has size zero, create an empty element.
    	if (languageBricks.size() == 0) {
    		languageBricks.add("");
    	}
    	System.out.println("languageText :" + languageText);
    	System.out.println("modifiedLanguageText :" + modifiedLanguageText);
    	System.out.println("languageComponent :" + languageComponent);
    	System.out.println("languageBrick :" + languageBricks);
    	System.out.println("languageBrick :" + languageBricks.size());
    	
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("language_component_id", languageComponent);
    	List<String> languageBricksFromDb = learnGermanDao.getWordList(params);
    	
    	String patternString = "\\b(" + StringUtils.join(languageBricksFromDb, "|") + ")\\b";
    	System.out.println("Pattern String is " + patternString);
    	String evaluationResultString  = "" ;

        Pattern pattern = Pattern.compile(patternString,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(languageText);

        int count = 0;
        int endIndexToTrack = 0 ;
        int correctCount = 0 ;
        while(matcher.find()) {
            count++;
            System.out.println("found: " + count + " : "
                    + matcher.start() + " - " + matcher.end() + " group: " + matcher.group());
            String valueToCompare  = languageBricks.get(count-1);
            evaluationResultString += languageText.substring(endIndexToTrack, matcher.start());
            endIndexToTrack = matcher.end();
             
            if (matcher.group().equals(valueToCompare)) {
            	evaluationResultString += "<font color=blue>" + valueToCompare + "</font>" ;
            	correctCount++;
            } else {
            	if (StringUtils.isBlank(valueToCompare)) {
            		evaluationResultString += "<b><font color=red>___</font></b>" ;
            	} else {
            		evaluationResultString += "<b><font color=red>" + valueToCompare + "</font></b>" ;
            	}
            }
        }
        evaluationResultString += languageText.substring(endIndexToTrack);
    	modelMap.addAttribute("evaluationResultString", evaluationResultString);
    	modelMap.addAttribute("languageText", languageText);
    	modelMap.addAttribute("totalCount", count);
    	modelMap.addAttribute("correctCount", correctCount);
    	modelMap.addAttribute("percentage", ((correctCount*100)/count));
    	return "evaluationResult";
    }

    	
    public String splitLanguageBricks(String passage, List<String> languageBrick) {
    	String labelToReplace = "<input type='text' name='languageBrick' size='10' value='' >";
		for (String brick : languageBrick) {
			passage = passage.replaceAll("\\b(?i)" + brick + "\\b", labelToReplace);
		}
		return passage;
    }
}