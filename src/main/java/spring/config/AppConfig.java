package spring.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(value = "spring")
@PropertySource("classpath:spring.properties")
public class AppConfig {

   @Autowired
   private Environment environment;

   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
      dataSource.setUrl("jdbc:mysql://localhost/springquickly");
      dataSource.setUsername("root");
      dataSource.setPassword("root");
      return dataSource;
   }

   @Bean
   public LocalSessionFactoryBean getSessionFactory() {
      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
      Properties properties = new Properties();
      sessionFactory.setDataSource(getDataSource());
      sessionFactory.setAnnotatedClasses(User.class);
      properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
      properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
      properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
      sessionFactory.setHibernateProperties(properties);
      return sessionFactory;
   }

   @Bean
   public HibernateTransactionManager getTransactionManager() {
      HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
      hibernateTransactionManager.setSessionFactory(getSessionFactory().getObject());
      return hibernateTransactionManager;
   }
}
