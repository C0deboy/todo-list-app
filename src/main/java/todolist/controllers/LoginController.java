package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import todolist.entities.User;
import todolist.services.UserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/")
    public String index(Model model) {

        User user = new User();
        model.addAttribute(user);

        return "index";
    }

    @PostMapping("/logIn")
    public String logIn(@ModelAttribute User user,  BindingResult result) {
        String username = user.getLogin();
        String password = user.getPassword();

        if (!userService.isUserNameValid(username)){
            result.rejectValue("login", "Invalid.login");
            return "index";
        }
        else if (!userService.isUserValid(username, password)) {
            result.rejectValue("password", "Invalid.password");
            return "index";
        }
        else {
            return "redirect:/" + user.getLogin() + "/your-todo-lists";
        }
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        User user = new User();
        model.addAttribute(user);

        return "signup";
    }


    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute User user, BindingResult result, Model model) {

        if (result.hasErrors()){
            return "signup";
        }
        else {
            userService.addUser(user);
            String signupSuccessMsg = messageSource.getMessage("Registration.success", new String[] {user.getLogin()}, Locale.ENGLISH);

            model.addAttribute("user", user);
            model.addAttribute("message", signupSuccessMsg);

            return "index";
        }
    }
}
