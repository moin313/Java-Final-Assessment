package Facebook.Post;

import org.springframework.util.MultiValueMap;

import java.util.List;

public interface PostDao {
    public List<Post> readAll();

    public boolean create(MultiValueMap<String, String> formData, String email);

    List<Post> readById(String email);

    public boolean update(Post post);

    public boolean delete(Post post);

}
