package home;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LearnGermanMapper {
	List<LanguageComponentVO> getLanguageComponentList();
	
	LanguageComponentVO getLanguageComponent(Map<String,Object> params);
	
	List<String> getWordList(Map<String,Object> params);


}
