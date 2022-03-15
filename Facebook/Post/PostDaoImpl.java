package Facebook.Post;

import Facebook.User.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import javax.persistence.Query;
import java.util.List;

@Component
public class PostDaoImpl implements PostDao{

    @Autowired
    private Session session;

    @Override
    public List<Post> readAll() {
        return session.createQuery("from Post", Post.class).getResultList();
    }

    @Override
    public boolean create(MultiValueMap<String, String> formData, String email) {
        try {
            session.beginTransaction();
            User user = session.get(User.class, email);
            String content = formData.get("content").get(0);

            Post post = new Post(content, user);

            session.persist(post);
            session.getTransaction().commit();
            System.out.println("added successfully");
            return true;
        }catch (Exception e){
            System.out.println("exception in adding "+ e);
            return false;
        }
    }

    @Override
    public List<Post> readById(String email) {
        try{
            Query query = session.createQuery("from Post where user_email=:email ");
            query.setParameter("email",email);
            List<Post> posts = (List<Post>) query.getResultList();
            return posts;
        }catch (Exception e){
                System.out.println("exception");
            return null;
        }
    }

    @Override
    public boolean update(Post post) {
        try{
            session.beginTransaction();
            Query query = session.createQuery("UPDATE Post SET post=:post WHERE id=:id");
            query.setParameter("id",post.getId());
            query.setParameter("post",post.getContent());

            int result = query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Result "+result);
            if (result != 0)
                return true;
            return false;
        }catch (Exception e) {
            System.out.println("Exception in update");
            return false;
        }
    }

    @Override
    public boolean delete(Post post) {
        try{
            session.beginTransaction();
            session.remove(post);
            session.getTransaction().commit();
            return true;
        } catch (Exception e){
            return false;
        }
    }
}