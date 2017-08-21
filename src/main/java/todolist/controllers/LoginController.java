package todolist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import todolist.entities.User;

@Controller
public class LoginController {
    @GetMapping("/")
    public String index(Model model) {

        User user = new User();
        model.addAttribute(user);

        return "index";
    }
}
