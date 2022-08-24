package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {

    private String loggedUser;

    @RequestMapping("/login")
    public String login(String login, String password) {
        if (password.equals("password")) {
            loggedUser=login;
        return "redirect:/minesweeper";
    }
        else {
        return "redirect:/gamestudio";}
    }

    @RequestMapping("/login_k")
    public String loginToKamene(String login, String password) {
        if (password.equals("password")) {
            loggedUser=login;
            return "redirect:/kamene";
        }
        else {
            return "redirect:/gamestudio";}
    }

    @RequestMapping("/logout")
    public String logout() {
            loggedUser=null;
            return "redirect:/gamestudio";//minesweeper
    }

    public String getLoggedUser() {
        return loggedUser;
    }
    public boolean isLogged() {
        return loggedUser != null;
    }


}
