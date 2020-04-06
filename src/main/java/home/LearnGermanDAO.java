package home;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LearnGermanDAO {

	@Autowired
	LearnGermanMapper learnGermanMapper;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public List<LanguageComponentVO> getLanguageComponentList() {
		List<LanguageComponentVO> languageComponentList = learnGermanMapper.getLanguageComponentList();
		return languageComponentList;
	}

	public LanguageComponentVO getLanguageComponent(Map<String,Object> params) {
		LanguageComponentVO languageComponentVO = learnGermanMapper.getLanguageComponent(params);
		return languageComponentVO;
	}	
	
	public List<String> getWordList(Map<String,Object> params) {
		List<String> wordList = learnGermanMapper.getWordList(params);
		return wordList;
	}
}
