package todolist.controllers;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.validation.beanvalidation.CustomValidatorBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import todolist.entities.User;
import todolist.services.EmailService;
import todolist.services.UserService;
import todolist.validators.ValidPassword;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Locale;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private EmailService emailService;


    @GetMapping(value = {"/"})
    public String index(Model model) {

        User user = new User();
        model.addAttribute(user);

        return "redirect:/login";
    }


    @GetMapping("/login")
    public String login(@ModelAttribute User user,  BindingResult result, String error, String logout, Model model) {

        if(logout != null) {
            String logoutMsg = messageSource.getMessage("Logout.success", null, Locale.ENGLISH);
            model.addAttribute("message", logoutMsg);
        }

        if (error != null) {
            result.rejectValue("username", "Invalid.credentials");
        }

        return "login";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(Model model) {


        String accessDeniedMsg = messageSource.getMessage("Access.denied", null, Locale.ENGLISH);
        model.addAttribute("message", accessDeniedMsg);

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

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model) {
        User user = new User();
        model.addAttribute(user);

        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@ModelAttribute User user, BindingResult result, Model model, HttpServletRequest request) throws NamingException {
        String userEmail = user.getEmail();
        if (userService.isEmailAvailable(userEmail)){
            result.rejectValue("email","ForgotPassword.wrongEmail");
            return "forgotPassword";
        }
        else {

            String token = UUID.randomUUID().toString();
            userService.insertResetTokenForEmail(token, userEmail);

            String appUrl = request.getScheme() + "://" + request.getServerName()+":"+request.getLocalPort();

            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("ellucky4@gmail.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password reset request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                    + "/resetPassword?token=" + token);

            emailService.sendSimpleEmail(passwordResetEmail);

            String signupSuccessMsg = messageSource.getMessage("ForgotPassword.success", null, Locale.ENGLISH);

            model.addAttribute("user", new User());
            model.addAttribute("message", signupSuccessMsg);

            return "forgotPassword";
        }
    }

    @GetMapping("/resetPassword")
    public String verifyToken(@RequestParam("token") String token, Model model) {
        User user = userService.getUserByResetPasswordToken(token);
        if (user == null) {
            String invalidLinkMsg = messageSource.getMessage("ResetToken.valid.false", null, Locale.ENGLISH);
            model.addAttribute("message", invalidLinkMsg);
        }
        User tempUser = new User();
        tempUser.setUsername(user.getUsername());
        tempUser.setId(user.getId());
        model.addAttribute("user", tempUser);
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute User user, @RequestParam String retypedPassword, BindingResult result, Model model) {
        model.addAttribute("user", user);

        if (!user.getPassword().equals(retypedPassword)) {
            result.rejectValue("password", "ResetPassword.passwordsNotEqual");
            return "resetPassword";
        }
        else {
            userService.changePassword(user, user.getPassword());
            String resetPasswordSuccesMsg = messageSource.getMessage("ResetPassword.success", null, Locale.ENGLISH);
            model.addAttribute("message", resetPasswordSuccesMsg);
        }
        return "resetPassword";
    }
}
