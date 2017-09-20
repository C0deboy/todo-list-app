package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;
import todolist.services.TodoListService;
import todolist.services.UserService;
import todolist.validators.PropertyValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PropertyValidator propertyValidator;

    private Principal principal;

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
    public String deleteUser(@PathVariable String username, Model model) {
        User user = userService.getUserByName(username);
        userService.removeUser(user);

        String signupSuccessMsg = messageSource.getMessage("UserSettings.deleteUser.success", new String[] {user.getUsername()}, Locale.ENGLISH);

        model.addAttribute("message", signupSuccessMsg);
        model.addAttribute("user", new User());

        return "login";
    }

    @GetMapping("/{username}/settings/changeEmail")
    public String changeEmail(Model model) {
        return "changeEmail";
    }

    @PostMapping("/{username}/settings/changeEmail")
    public String changeEmail(@PathVariable String username, @ModelAttribute User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        String property = "email";

        if(propertyValidator.isPropertyNotValid(property, user)){
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

        return "redirect:./";
    }

}
