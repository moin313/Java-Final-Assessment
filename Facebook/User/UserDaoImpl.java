package Facebook.User;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import javax.persistence.Query;


@Component
public class UserDaoImpl implements UserDao{
    private String loggedInUserEmail = null;

    @Autowired
    Session session;


    /*

     */
    @Override
    public boolean addNewUser(MultiValueMap<String, String> formData) {
        try{
            String email = formData.get("email").get(0);
            String name = formData.get("name").get(0);
            String pass = formData.get("password").get(0);
//            System.out.println(email + " " + name + " " + pass);
            User user = new User(name, pass, email);
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
//            formData.clear();
            return true;
        }catch(Exception e){
            return false;
        }
    }


    /*----------------------------------------
    THIS FUNCTION WILL CHECK USER EXISTANCE IN
    DATABASE.
     ---------------------------------------*/
    @Override
    public boolean isUserExist(String email) {
        try {
            String hql = "select email from User where email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            System.out.println(query.getSingleResult()+"Is user exist "+email.equals(query.getSingleResult()));
            return  email.equals(query.getSingleResult());
        }catch (Exception e){
            return false;
        }
    }


    /*----------------------------------------
    THIS FUNCTION WILL VALIDATE USER.
     ---------------------------------------*/
    @Override
    public boolean isValidUser(MultiValueMap<String, String> formData) {
        String email = formData.get("email").get(0);
        String password = formData.get("password").get(0);
        try {
//            session.beginTransaction();
            User user = session.get(User.class, email);
            if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
                System.out.println("valid user ");
                setLoggedInUserDetail(email);
                return true;
            }else {
                System.out.println("invalid user");
                return false;
            }
        }catch (Exception e){
            System.out.println(e+" exception");
            return false;
        }
    }

    private void setLoggedInUserDetail(String email) {
        loggedInUserEmail = email;
    }

    @Override
    public String getLoggedInUserEmail() {
//        System.out.println("inside getLoggedInUserEmail function");
        return loggedInUserEmail;
    }

    @Override
    public User getUserProfile(String email) {
        try{
            User user = session.get(User.class, email);
            System.out.println("96 "+user.getEmail());
            return user;
        }catch (Exception e){
            System.out.println("exception in getUserProfile "+ e);
        }
        return null;
    }


}
