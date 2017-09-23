package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import todolist.entities.User;
import todolist.services.UserService;
import todolist.validators.PropertyValidator;

import java.util.Locale;

@Controller
public class UserController {

    private final UserService userService;

    private final MessageSource messageSource;

    private final PropertyValidator<User> propertyValidator;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource, PropertyValidator<User> propertyValidator) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.propertyValidator = propertyValidator;
    }

    @ModelAttribute("user")
    public User newUser() {
        return new User();
    }

    @GetMapping("/{username}/settings")
    public String getSettingsPage(Model model, @PathVariable String username) {

        User user = userService.getUserByName(username);

        model.addAttribute(user);

        return "settings";
    }

    @GetMapping("/{username}/settings/deleteUser")
    public String deleteUser(@PathVariable String username, RedirectAttributes redirectAttributes) {
        User user = userService.getUserByName(username);
        userService.removeUser(user);

        String signupSuccessMsg = messageSource.getMessage("UserSettings.deleteUser.success", new String[] {user.getUsername()}, Locale.ENGLISH);

        redirectAttributes.addFlashAttribute("message", signupSuccessMsg);
        redirectAttributes.addFlashAttribute("user", new User());

        return "redirect:/login";
    }

    @GetMapping("/{username}/settings/changeEmail")
    public String changeEmail(@PathVariable String username, Model model) {
        User user = (User) model.asMap().get("user");
        if(user.getUsername() == null){
            user.setUsername(username);
        }
        model.addAttribute(user);

        return "changeEmail";
    }

    @PostMapping("/{username}/settings/changeEmail")
    public String changeEmail(@PathVariable String username, @ModelAttribute User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        String property = "email";

        if(propertyValidator.isPropertyValid(property, user)){
            result = propertyValidator.addErrorsForBindingResultIfPresent(result);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);

            return "redirect:./changeEmail";
        }
        else {
            userService.changeEmail(username, user.getEmail());

            String signupSuccessMsg = messageSource.getMessage("UserSettings.changeEmail.success", null, Locale.ENGLISH);

            redirectAttributes.addFlashAttribute("message", signupSuccessMsg);
        }

        return "redirect:/" + username + "/settings";
    }

}
