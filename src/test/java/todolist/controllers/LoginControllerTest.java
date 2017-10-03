package todolist.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import todolist.entities.User;
import todolist.services.EmailService;
import todolist.services.UserService;
import todolist.validators.PropertyValidator;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/appTestconfig-root.xml")
@WebAppConfiguration
public class LoginControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Mock
    UserService userService;

    @Mock
    MessageSource messageSource;

    @Mock
    EmailService emailService;

    @Mock
    PropertyValidator<User> propertyValidator;

    @InjectMocks
    LoginController loginController;

    public LoginControllerTest() {
    }

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
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
    }

    @Test
    public void shouldRedirectToMainPageWhenUserLoggedIn() throws Exception {

        mockMvc.perform(get("/login")
                        .with(user("Codeboy")
                            .password("root")
                            .authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/Codeboy/your-todo-lists"));
    }

    @Test
    public void shouldLogoutUserAtSpecificUrl() throws Exception {

        mockMvc.perform(get("/logout")
                        .with(user("Codeboy")
                                .password("root")
                                .authorities(new SimpleGrantedAuthority("USER"))))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/logoutSuccess"));
    }

    @Test
    public void logoutPageShouldContainMessage() throws Exception {
        mockMvc.perform(get("/logoutSuccess")
                        .with(user("Codeboy")
                                .password("root")
                                .authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    public void attemptToLoginWithInvalidCredentialsRusultsLoginFailure() throws Exception {
        mockMvc.perform(post("/login")
                        .with(user("user")
                                .password("user")))
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
                        "Invalid.credentials"
                ));
    }
}