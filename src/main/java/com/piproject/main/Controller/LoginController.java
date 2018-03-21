package com.piproject.main.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class LoginController {
    public String check;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String LoginForm(){

        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String verifyLogin(@RequestParam String userId, @RequestParam String pass, Model model){

       User user = new User(userId, pass);
        check = user.check(user.username, user.password);

        if (check == null){
            model.addAttribute("loginError", "Error logging in");
            return "login";
        }
        return "redirect:/home";

    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home (Model model){

        model.addAttribute("loggedInUser", "admin");
        return "home";
    }

    @RequestMapping (value = "/logout", method = RequestMethod.GET)
    public String logout(){

        return "login";
    }


}

