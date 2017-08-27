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

    @GetMapping(value = {"/"})
    public String index(Model model) {

        User user = new User();
        model.addAttribute(user);

        return "redirect:/login";
    }


    @GetMapping("/login")
    public String login(@ModelAttribute User user,  BindingResult result, String error, String logout, Model model) {

        if(logout != null) {
            String logoutMsg = messageSource.getMessage("Logout.success", new String[]{}, Locale.ENGLISH);
            model.addAttribute("message", logoutMsg);
        }

        if (error != null) {
            result.rejectValue("username", "Invalid.credentials");
        }

        return "login";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model) {


        String logoutMsg = messageSource.getMessage("Access.denied", new String[]{}, Locale.ENGLISH);
        model.addAttribute("message", logoutMsg);

        return "access-denied";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        User user = new User();
        model.addAttribute(user);

        return "signup";
    }


    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute User user, BindingResult result, Model model) {

        if (result.hasErrors()){
            return "signup";
        }
        else {
            userService.addUser(user);
            String signupSuccessMsg = messageSource.getMessage("Registration.success", new String[] {user.getUsername()}, Locale.ENGLISH);

            model.addAttribute("user", user);
            model.addAttribute("message", signupSuccessMsg);

            return "login";
        }
    }
}
