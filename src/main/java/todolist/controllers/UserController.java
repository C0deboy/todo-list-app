package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todolist.entities.Task;
import todolist.entities.TodoList;
import todolist.entities.User;
import todolist.services.TodoListService;
import todolist.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/{username}/settings")
    public String getSettingsPage(@AuthenticationPrincipal Principal principal, @PathVariable String username, Model model) {
        String principalName = principal.getName();

        if (!principalName.equals(username)) {
            return "redirect:/"+principalName+"/settings";
        }

        User user = userService.getUserByName(principalName);

        model.addAttribute(user);

        return "settings";
    }

    @GetMapping(value = "/{user}/settings/deleteUser")
    public String deleteUser(@PathVariable("user") String username, Model model) {
        User user = userService.getUserByName(username);
        userService.removeUser(user);

        String signupSuccessMsg = messageSource.getMessage("UserSettings.deleteUser.success", new String[] {user.getUsername()}, Locale.ENGLISH);

        model.addAttribute("message", signupSuccessMsg);
        model.addAttribute("user", new User());

        return "login";
    }

}
