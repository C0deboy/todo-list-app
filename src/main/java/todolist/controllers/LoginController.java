package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import todolist.entities.User;
import todolist.services.EmailService;
import todolist.services.UserService;
import todolist.validators.PropertyValidator;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.UUID;

@Controller
public class LoginController {
    private final UserService userService;

    private final MessageSource messageSource;

    private final EmailService emailService;

    private final PropertyValidator<User> propertyValidator;

    @Autowired
    public LoginController(UserService userService, MessageSource messageSource, EmailService emailService, PropertyValidator<User> propertyValidator) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.emailService = emailService;
        this.propertyValidator = propertyValidator;
    }


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

        String property = "email";

        if(propertyValidator.isPropertyNotValid(property, user)){
            result = propertyValidator.addErrorsForBindingResultIfPresent(result);
        }
        if (userService.isEmailAvailable(userEmail)){
            result.rejectValue(property,"ForgotPassword.wrongEmail");
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
        }

        return "forgotPassword";
    }

    @GetMapping("/resetPassword")
    public String verifyToken(@RequestParam("token") String token, Model model) {

        User user = userService.getUserByResetPasswordToken(token);

        if (user == null) {
            String invalidLinkMsg = messageSource.getMessage("ResetToken.valid.false", null, Locale.ENGLISH);
            model.addAttribute("message", invalidLinkMsg);
        }
        else {
            model.addAttribute("user", user);
        }

        return "resetPassword";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingToken(MissingServletRequestParameterException e) {
        return "redirect:forgotPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute User user, @RequestParam String retypedPassword, BindingResult result, Model model) {
        model.addAttribute("user", user);

        String property = "password";

        if(propertyValidator.isPropertyNotValid(property, user)){
            result = propertyValidator.addErrorsForBindingResultIfPresent(result);
        }
        else if (!user.getPassword().equals(retypedPassword)) {
            result.rejectValue("password", "ResetPassword.passwordsNotEqual");
        }
        else {
            userService.changePassword(user, user.getPassword());

            String resetPasswordSuccesMsg = messageSource.getMessage("ResetPassword.success", null, Locale.ENGLISH);
            model.addAttribute("message", resetPasswordSuccesMsg);
        }

        return "resetPassword";
    }
}
