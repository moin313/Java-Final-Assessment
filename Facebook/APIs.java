package Facebook;

import Facebook.Post.Post;
import Facebook.Post.PostDao;
import Facebook.User.User;
import Facebook.User.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class APIs {
    @Autowired
    UserDao userDao;
    @Autowired
    PostDao postDao;

    /*--------------------------------------------
    GENERAL UTILITY METHOD
     ---------------------------------------------*/
    private ModelAndView errorMessageModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("message", message);
        return modelAndView;
    }


    /*-------------------------------------------
      ADD NEW USER FORM FOR TAKING INPUT FROM USER
      AND ADD NEW USER WILL GET USER INPUT FROM THE
      FORM AND REGISTER THAT USER BY STORING DETAILS
      IN DATABASE.
    ---------------------------------------------- */
    @GetMapping("/addNewUserForm")
    public ModelAndView addUserForm() {
        ModelAndView modelAndView = new ModelAndView("addNewUser");
        return modelAndView;
    }
    @PostMapping(value = "/addNewUser", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView addNewUser(@RequestBody MultiValueMap<String, String> formData) {
        if(userDao.isUserExist(formData.get("email").get(0))){
            System.out.println("User Already exist");
        }else{
            System.out.println("user registered successfully");
            userDao.addNewUser(formData);
        }
//        formData.clear();
        ModelAndView modelAndView = new ModelAndView("addNewUser");
        return modelAndView;
    }



    /*-------------------------------------------
      LOGIN FORM TAKES INPUT FROM USER AND SEND
      IT TO VALIDATE METHOD.
    ---------------------------------------------- */
    @GetMapping("/fbLoginForm")
    public ModelAndView loginForm() {
        ModelAndView modelAndView = new ModelAndView("fbLogin");
        return modelAndView;
    }
    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView validate(@RequestBody MultiValueMap<String, String> formData) {
        if (!userDao.isValidUser(formData)) {
            return errorMessageModelAndView("Wrong credentials");
        }
        ModelAndView modelAndView = getUserDetails();
        return modelAndView;
    }


    /*----------------------------------------------------------

    ----------------------------------------------------------*/
    @GetMapping("/createPost")
    public ModelAndView createPost() {
        ModelAndView modelAndView = new ModelAndView("createPost");
        return modelAndView;
    }
    @PostMapping(value = "/addPost", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView addPost(@RequestBody MultiValueMap<String, String> formData) {
        if (userDao.getLoggedInUserEmail() == null) {
            return errorMessageModelAndView("Please login first");
        }
        System.out.println("add post 94 "+userDao.getLoggedInUserEmail());
        postDao.create(formData, userDao.getLoggedInUserEmail());
        ModelAndView modelAndView = getUserDetails();
        return modelAndView;
    }



    /*--------------------------------------------------------------------------
    DISPLAY USER DETAILS
    * ---------------------------------------------------------------------------*/
   /* @GetMapping("/displayUserDetails")
    public ModelAndView getUserDetails() {
        ModelAndView modelAndView = new ModelAndView("users");

        User user = userDao.getUserProfile(userDao.getLoggedInUserEmail());

        modelAndView.getModel().put("name", user.getName());
        modelAndView.getModel().put("email", user.getEmail());

//        System.out.println("50  "+userProfile.entrySet().getClass().getName());
        String userEmail = user.getEmail();
        List<Post> posts = postDao.readById(userEmail);

//        for (Map.Entry entry : userProfile.entrySet()) {
//            users.add((User) entry.getValue());
//        }
//        System.out.println("Type %%%%%%% "+ users.getClass().getName());
        modelAndView.getModel().put("users", posts);
        return modelAndView;
    }*/
    @GetMapping("/displayUserDetails")
    public ModelAndView getUserDetails() {
        ModelAndView modelAndView = new ModelAndView("users");
        Map<Integer, Post> userProfile = new HashMap<>();

        String email = userDao.getLoggedInUserEmail();
        User user = userDao.getUserProfile(email);
        modelAndView.getModel().put("Name", user.getName());
        modelAndView.getModel().put("Email", user.getEmail());

        System.out.println(postDao.readById(email));
        List<Post> temp = postDao.readById(email);
        System.out.println(temp.get(0).getContent());
//        Integer i = 0;
        for (int i=0; i < temp.size(); i++) {
            userProfile.put(i, (Post) temp.get(i));
        }

        Facebook.User.User user1 = new Facebook.User.User("b","b","b");

//        Post post = new Post("asd", user1);

//        userProfile.put("emails",post );

//        Post post1 = new Post("asd", user1);

//        userProfile.put("emails1",post1 );
        System.out.println("50  "+userProfile.entrySet().getClass().getName());
        List<Post> Posts = new ArrayList<>();
        for (Map.Entry entry : userProfile.entrySet()) {
            Posts.add((Post) entry.getValue());
        }
//        System.out.println("Type %%%%%%% "+ post.getClass().getName());
        modelAndView.getModel().put("users", Posts);
        return modelAndView;
    }


    /*

    @GetMapping("/displayUserDetails")
    public ModelAndView getUserDetails() {
        ModelAndView modelAndView = new ModelAndView("profile");
//        if (userDao.)
//            allAccDetails();
        System.out.println("117");
        User user = userDao.getUserProfile(userDao.getLoggedInUserEmail());
        modelAndView.getModel().put("name", user.getName());
        modelAndView.getModel().put("email", user.getEmail());
        System.out.println(user.getEmail()+" "+user.getName());

        List<Post> posts =
        System.out.println(posts.get(0));
         //                for (Map.Entry entry : userProfile.entrySet()) {

//                        <label><b>{{User.name}}</b></label><br/>//                    users.add((User) entry.getValue());
//                }
        System.out.println("inside getUserDetails ");//posts.get(0).getClass().getName());
//        for(int i=0; i < posts.size(); i++){
//            System.out.println(posts.get(i).getContent().getClass().getName());
//            modelAndView.getModel().put("posts", posts.get(i).getContent());
//        }
        modelAndView.getModel().put("posts", posts.get(0));
        return modelAndView;
    }*/
}
