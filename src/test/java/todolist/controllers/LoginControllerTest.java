package todolist.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import todolist.entities.User;
import todolist.services.EmailService;
import todolist.services.UserService;
import todolist.validators.PropertyValidator;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/appTestconfig-root.xml")
@WebAppConfiguration
public class LoginControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private FilterChainProxy filterChainProxy;

  @Mock
  private UserService userService;

  @Mock
  private MessageSource messageSource;

  @Mock
  private EmailService emailService;

  @Spy
  @Autowired
  private PropertyValidator<User> propertyValidator;

  @InjectMocks
  private LoginController loginController;

  public LoginControllerTest() {
  }

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);

    when(messageSource.getMessage(any(), any(), any())).thenReturn("mockedMessage");

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/jsp/view/");
    viewResolver.setSuffix(".jsp");

    mockMvc = MockMvcBuilders.standaloneSetup(loginController)
        .apply(springSecurity(filterChainProxy))
        .setViewResolvers(viewResolver)
        .build();
  }

  @Test
  public void redirectToLoginPage() throws Exception {

    mockMvc.perform(get("/"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/login"))
        .andExpect(model().attribute("user", new User()));
  }

  @Test
  public void accessToTypedUrlShouldBeForbbidenForWrongUser() throws Exception {

    mockMvc.perform(get("/Codeboy/your-todo-lists")
        .with(user("user").password("user")))
        .andExpect(forwardedUrl("/accessDenied"));
  }

  @Test
  public void accessDeniedPageShouldContainMessage() throws Exception {

    mockMvc.perform(get("/accessDenied"))
        .andExpect(status().isOk())
        .andExpect(view().name("access-denied"))
        .andExpect(model().attributeExists("message"));

    verify(messageSource, times(1)).getMessage("Access.denied", null, Locale.ENGLISH);
  }

  @Test
  @WithMockUser(username = "Codeboy", authorities = "USER")
  public void shouldRedirectToMainPageWhenUserLoggedIn() throws Exception {

    mockMvc.perform(get("/login"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/Codeboy/your-todo-lists"));
  }

  @Test
  public void shouldLogoutUserAtSpecificUrl() throws Exception {

    mockMvc.perform(get("/logout"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/logoutSuccess"));
  }

  @Test
  public void logoutPageShouldContainMessage() throws Exception {
    mockMvc.perform(get("/logoutSuccess"))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andExpect(model().attributeExists("message", "user"));

    verify(messageSource).getMessage("Logout.success", null, Locale.ENGLISH);
  }

  @Test
  @WithMockUser(username = "user", password = "user")
  public void attemptToLoginWithInvalidCredentialsRusultsLoginFailure() throws Exception {
    mockMvc.perform(post("/login"))
        .andExpect(status().isOk())
        .andExpect(forwardedUrl("/loginFailure"));
  }

  @Test
  public void atFailedLoginUserGetsMessage() throws Exception {
    mockMvc.perform(post("/loginFailure"))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andExpect(model().attributeHasFieldErrorCode(
            "user",
            "username",
            "Invalid.credentials"));
  }

  @Test
  public void userExistsInModelAtSignup() throws Exception {
    mockMvc.perform(get("/signup"))
        .andExpect(status().isOk())
        .andExpect(view().name("signup"))
        .andExpect(model().attributeExists("user"));
  }

  @Test
  public void signupFailsAndThrowsErrorsWhenInvalidUserData() throws Exception {

    User user = new User("ts", "less8", "em.pl");
    String userBindingResult = "org.springframework.validation.BindingResult.user";

    FlashMap flashMap = mockMvc.perform(post("/signup").flashAttr("user", user))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/signup"))
        .andExpect(flash().attributeExists(userBindingResult, "user"))
        .andReturn().getFlashMap();


    BindingResult result = (BindingResult) flashMap.get(userBindingResult);

    assertEquals("Should be 4 errors", 4, result.getErrorCount());
  }

  @Test
  public void signupSucceedWhenValidUserData() throws Exception {
    User user = new User("Test", "Test$123", "test@gmail.com");

    String userBindingResult = "org.springframework.validation.BindingResult.user";

    FlashMap flashMap = mockMvc.perform(post("/signup").flashAttr("user", user))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/login"))
        .andExpect(flash().attributeExists("message"))
        .andReturn().getFlashMap();

    BindingResult result = (BindingResult) flashMap.get(userBindingResult);

    assertNull("Should be 0 errors", result);

    verify(messageSource, times(1)).getMessage("Registration.success",
        new String[] {user.getUsername()}, Locale.ENGLISH);
  }

  @Test
  public void forgotPasswordContainsUserModel() throws Exception {
    mockMvc.perform(get("/forgotPassword"))
        .andExpect(status().isOk())
        .andExpect(view().name("forgotPassword"))
        .andExpect(model().attributeExists("user"));
  }

  @Test
  public void whenUserTypesInvalidEmailInForgotPasswordThenGetsMessage() throws Exception {

    User user = new User("", "", "wrong.pl");
    String userBindingResult = "org.springframework.validation.BindingResult.user";

    FlashMap flashMap = mockMvc.perform(post("/forgotPassword").flashAttr("user", user))
        .andExpect(status().isFound())
        .andExpect(flash().attributeExists(userBindingResult, "user"))
        .andExpect(redirectedUrl("/forgotPassword"))
        .andReturn().getFlashMap();

    BindingResult result = (BindingResult) flashMap.get(userBindingResult);

    assertEquals(1, result.getErrorCount());

    verify(propertyValidator).disabledValidationForErrorCode("ValidEmail.user.email");
    verify(propertyValidator).isPropertyValid("email", user);
    verify(propertyValidator).addErrorsForBindingResultIfPresent(any(BindingResult.class));
  }

  @Test
  public void whenUserTypesValidEmailInForgotPasswordThenThenGetsMessageAndLinkIsSent() throws Exception {
    when(emailService.prepareMessage(anyString(), anyString(), anyString())).thenReturn(new SimpleMailMessage());

    User user = new User("", "", "root@gmail.com");
    String userBindingResult = "org.springframework.validation.BindingResult.user";

    FlashMap flashMap = mockMvc.perform(post("/forgotPassword").flashAttr("user", user))
        .andExpect(status().isFound())
        .andExpect(flash().attributeExists("message", "user"))
        .andExpect(redirectedUrl("/forgotPassword"))
        .andReturn().getFlashMap();

    BindingResult result = (BindingResult) flashMap.get(userBindingResult);

    assertTrue("Should be no errors", result.getErrorCount() == 0);

    verify(userService).insertResetTokenForEmail(anyString(), anyString());
    verify(emailService).prepareMessage(anyString(), anyString(), anyString());
    verify(emailService).sendSimpleEmail(any(SimpleMailMessage.class));
    verify(messageSource).getMessage("ForgotPassword.success", null, Locale.ENGLISH);

  }
}