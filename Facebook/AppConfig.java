package Facebook;

import Facebook.Post.Post;
import Facebook.User.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Session getSession(){
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Post.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory.openSession();
    }
}

