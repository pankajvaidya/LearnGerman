package home;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

	@RequestMapping("/")
		public String index() {
			return "index";
	}
	   
    @RequestMapping("/select")
    public String home(ModelMap modelMap) {
    	List<Map<String,Object>> languageComponentList = jdbcTemplate.queryForList
    			("select language_component_id, language_component_name from language_component order by sequence_no");
    	modelMap.addAttribute("languageComponentList", languageComponentList);
        return "select";
    }

    @RequestMapping("/blankOutLangageComponent")
    public String blankOutLangageComponent(@RequestParam("languageText") String languageText,
    		@RequestParam("languageComponent") String languageComponent, ModelMap modelMap) {
    	System.out.println("languageComponent :" + languageComponent);
    	System.out.println("languageText :" + languageText);
    	languageText = StringEscapeUtils.escapeHtml4(languageText);

    	modelMap.addAttribute("languageComponent", languageComponent);
    	modelMap.addAttribute("languageText", languageText);
    	
    	String query = " select word_name from word where language_component_id = " + languageComponent ;
    
    	List<String> languageBrick = jdbcTemplate.queryForList(query, String.class);
    	String modifiedLanguageText = splitLanguageBricks(languageText,languageBrick);
    	modelMap.addAttribute("modifiedLanguageText", modifiedLanguageText);

    	System.out.println(languageBrick);
    	System.out.println("Size is : " + languageBrick.size());
    	System.out.println("done");
        return "evaluate";
    }    

    @RequestMapping("/evaluateLangageComponent")
    public String evaluateLangageComponent(@RequestParam("languageText") String languageText,
    		@RequestParam("modifiedLanguageText") String modifiedLanguageText,
    		@RequestParam("languageComponent") String languageComponent,
    		@RequestParam("languageBrick") List<String> languageBricks,
    		ModelMap modelMap) {
    	
    	System.out.println("modifiedLanguageText :" + modifiedLanguageText);
    	System.out.println("languageText :" + languageText);
    	System.out.println("languageBrick :" + languageBricks);
    	System.out.println("languageBrick :" + languageBricks.size());
    	
    	String query = " select word_name from word where language_component_id = " + languageComponent ;
    	
    	List<String> languageBricksFromDb = jdbcTemplate.queryForList(query, String.class);
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