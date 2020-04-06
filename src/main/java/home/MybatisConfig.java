package home;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.inject.Named;

import javax.sql.DataSource;

@Configuration
public class MybatisConfig {
	
	@Bean(name = "learnGermanSessionFactory") 
	public SqlSessionFactoryBean learnGermanSqlSessionFactory(@Named("learnGermanDataSource") 
		final DataSource learnGermanDataSource) throws Exception {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(learnGermanDataSource);
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		sqlSessionFactoryBean.setTypeAliasesPackage("home");
		sqlSessionFactory.getConfiguration().addMapper(LearnGermanMapper.class);
		sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/LearnGermanMapper.xml"));
		return sqlSessionFactoryBean;
	}
	
	@Bean
	public MapperFactoryBean<LearnGermanMapper> dbMapper(@Named("learnGermanSessionFactory") final SqlSessionFactoryBean sqlSessionFactoryBean)
		throws Exception {
		MapperFactoryBean<LearnGermanMapper> factoryBean = new MapperFactoryBean<>(LearnGermanMapper.class);
		factoryBean.setSqlSessionFactory(sqlSessionFactoryBean.getObject());
		return factoryBean;
	}
	
}
