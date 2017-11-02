package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import todolist.entities.User;
import todolist.services.EmailService;
import todolist.services.UserService;
import todolist.validators.PropertyValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.UUID;


@Controller
public class UserController {

  private final UserService userService;

  private final MessageSource messageSource;

  private final PropertyValidator<User> propertyValidator;

  private final EmailService emailService;

  @Autowired
  public UserController(UserService userService, MessageSource messageSource,
                        PropertyValidator<User> propertyValidator, EmailService emailService) {
    this.userService = userService;
    this.messageSource = messageSource;
    this.propertyValidator = propertyValidator;
    this.emailService = emailService;
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
  public String deleteUser(@PathVariable String username, RedirectAttributes redirectAttributes,
                           HttpServletRequest httpServletRequest) {
    try {
      httpServletRequest.logout();
    } catch (ServletException e) {
      e.printStackTrace();
    }

    User user = userService.getUserByName(username);
    userService.removeUser(user);

    String signupSuccessMsg = messageSource.getMessage("UserSettings.deleteUser.success",
        new String[] {user.getUsername()}, Locale.ENGLISH);

    redirectAttributes.addFlashAttribute("message", signupSuccessMsg);

    return "redirect:/login";
  }

  @GetMapping("/{username}/settings/changeEmail")
  public String changeEmail(@PathVariable String username, Model model) {
    User user = (User) model.asMap().get("user");
    if (user.getUsername() == null) {
      user.setUsername(username);
    }
    model.addAttribute(user);

    return "changeEmail";
  }

  @PostMapping("/{username}/settings/changeEmail")
  public String changeEmail(@PathVariable String username, @ModelAttribute User user,
                            BindingResult result, RedirectAttributes redirectAttributes) {

    if (!propertyValidator.isPropertyValid("email", user)) {
      result = propertyValidator.addErrorsForBindingResultIfPresent(result);

      String userBindingResult = "org.springframework.validation.BindingResult.user";
      redirectAttributes.addFlashAttribute(userBindingResult, result);
      redirectAttributes.addFlashAttribute("user", user);

      return "redirect:./changeEmail";
    } else {

      userService.changeEmail(username, user.getEmail());

      String signupSuccessMsg = messageSource.getMessage("UserSettings.changeEmail.success",
          null, Locale.ENGLISH);

      redirectAttributes.addFlashAttribute("message", signupSuccessMsg);
    }

    return "redirect:/" + username + "/settings";
  }

  @GetMapping("/{username}/settings/changePassword")
  public String changeEmail(@PathVariable String username, HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {

    User user = userService.getUserByName(username);
    String token = UUID.randomUUID().toString();
    userService.insertResetTokenForEmail(token, user.getEmail());

    String domain = request.getScheme() + "://" + request.getServerName() + ":"
        + request.getLocalPort();


    String to = user.getEmail();
    String subject = "Password reset request";
    String message = "To reset your password, click the link below:\n" + domain
        + "/resetPassword?token=" + token;
    SimpleMailMessage passwordResetEmail = emailService.prepareMessage(to, subject, message);

    emailService.sendSimpleEmail(passwordResetEmail);

    String signupSuccessMsg = messageSource.getMessage("ForgotPassword.success",
        null, Locale.ENGLISH);

    redirectAttributes.addFlashAttribute("message", signupSuccessMsg);

    return "redirect:/" + username + "/settings";
  }

}
