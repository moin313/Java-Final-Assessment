package Facebook.User;

import org.springframework.util.MultiValueMap;

public interface UserDao {
    boolean addNewUser(MultiValueMap<String, String> formData);

    boolean isUserExist(String email);

    boolean isValidUser(MultiValueMap<String, String> formData);

    public String getLoggedInUserEmail();

    User getUserProfile(String email);
}
