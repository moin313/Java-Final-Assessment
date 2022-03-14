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
    public void validate(@RequestBody MultiValueMap<String, String> formData) {
        if (!userDao.isValidUser(formData)) {
//            return errorMessageModelAndView("Wrong credentials");
            System.out.println("Wrong credentials");
        }
//        formData.clear();
        getUserDetails();
//        ModelAndView modelAndView = new ModelAndView("profile");
//        modelAndView.getModel().put("name", "Login");
//        modelAndView.getModel().put("email", "success");
//        return modelAndView;
    }


    /*----------------------------------------------------------

    ----------------------------------------------------------*/
    @GetMapping("/createPost")
    public ModelAndView createPost() {
        ModelAndView modelAndView = new ModelAndView("createPost");
        return modelAndView;
    }
    @PostMapping(value = "/addPost", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void addPost(@RequestBody MultiValueMap<String, String> formData) {
        if (userDao.getLoggedInUserEmail() == null) {
            System.out.println("Please login first");
//            return errorMessageModelAndView("Please login first");
        }
        System.out.println("add post 94 "+userDao.getLoggedInUserEmail());
        postDao.create(formData, userDao.getLoggedInUserEmail());
        getUserDetails();
//        ModelAndView modelAndView = new ModelAndView("profile");
//        modelAndView.getModel().put("name", "Login");
//        modelAndView.getModel().put("email", "success");
    }



    /*--------------------------------------------------------------------------
    DISPLAY USER DETAILS
    * ---------------------------------------------------------------------------*/
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

        List<Post> posts = postDao.readById(user.getEmail());

         //                for (Map.Entry entry : userProfile.entrySet()) {
//                    users.add((User) entry.getValue());
//                }
        System.out.println("inside getUserDetails ");//posts.get(0).getClass().getName());
        for(int i=0; i < posts.size(); i++){
            System.out.println(posts.get(i).getContent().getClass().getName());
            modelAndView.getModel().put("posts", posts.get(i).getContent());
        }
        return modelAndView;
    }
}
