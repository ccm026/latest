package com.piproject.main.Controller;

public class User {

    String username;
    String password;

    public User(String name, String pas) {
        username = name;
        password = pas;
    }

    public String check(String name, String pass) {

        if (name.equals("admin") && (pass.equals("password"))) {
            return "OK";
        }
        return null;

    }
}



