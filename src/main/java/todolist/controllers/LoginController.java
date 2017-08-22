package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import todolist.entities.User;
import todolist.services.UserService;

import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {

        User user = new User();
        model.addAttribute(user);

        return "index";
    }

    @PostMapping("/logIn")
    public String logIn(@Valid @ModelAttribute User user, BindingResult result) {

        String userName = user.getLogin();
        String password = user.getPassword();
        System.out.println("*"+password+"*");

        if (!userService.isUserNameValid(userName)){
            result.rejectValue("login", "invalidLogin", "Invalid login.");
            return "index";
        }
        else if (!userService.isPasswordValid(userName, password)) {
            result.rejectValue("password", "invalidPassword","Invalid password");
            return "index";
        }
        else {
            return "redirect:/" + userName + "/your-todo-lists";
        }
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        User user = new User();
        model.addAttribute(user);

        return "signup";
    }


    @PostMapping("/signUp")
    public String signUp(@ModelAttribute @Valid User user, BindingResult result, Model model) {

        if (!userService.isEmailAvailable(user.getEmail())){
            result.rejectValue("email", "invalidEmail", "This email already exists.");

            return "signup";
        }
        else if (userService.isUserNameValid(user.getLogin())){
            result.rejectValue("login", "invalidLogin", "This username already exists.");

            return "signup";
        }
        else if (result.hasErrors()){
            return "signup";
        }

        userService.addUser(user);
        model.addAttribute("message",  "Your account has been created. Log in.");

        return "index";

    }
}
