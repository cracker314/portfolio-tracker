package org.rakana.portfoliotracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
//@ResponseBody  // added in this location ie above controller class as all methods in the class have this annotation
public class HelloController {

    // Handles request at path /hello
//    @GetMapping("hello")
//    @ResponseBody // returns plain text
//    public String hello() {
//        return "Hello, Spring!";
//    }

    // Handles request at path /goodbye
    @GetMapping("goodbye")
    @ResponseBody
    public String goodbye() {
        return "Goodbye, Spring!";
    }

    // Handles request of the form /hello?name=LaunchCode (Query Parameter)
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "hello")
    public String helloWithQueryParam(@RequestParam String name, Model model) {
        String greeting = "Hello, " + name + "!";
        model.addAttribute("greeting", greeting);
        return "hello";
    }

    // Handles request of the form /hello/LaunchCode (Path parameter)
    @GetMapping("hello/{name}")
    public String helloWithPathParam(@PathVariable String name, Model model) {
        model.addAttribute("greeting", "Hello, " + name + "!");
        return "hello";
    }

    @GetMapping("form")
    public String helloWithForm() {
        return "form";
    }

    @GetMapping("hello-names")
    public String helloNames(Model model) {
        List<String> names = new ArrayList<>();
        names.add("ramanujam");
        names.add("kannan");
        names.add("naren");
        model.addAttribute("names", names);
        return "hello-list";
    }

}
