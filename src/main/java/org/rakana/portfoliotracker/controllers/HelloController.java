package org.rakana.portfoliotracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody // added in this location ie above controller class as all methods in the class have this annotation
public class HelloController {

    // Handles request at path /hello
//    @GetMapping("hello")
//    @ResponseBody // returns plain text
//    public String hello() {
//        return "Hello, Spring!";
//    }

    // Handles request at path /goodbye
    @GetMapping("goodbye")
    public String goodbye() {
        return "Goodbye, Spring!";
    }

    // Handles request of the form /hello?name=LaunchCode (Query Parameter)
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "hello")
    public String helloWithQueryParam(@RequestParam String name) { return "Hello, " + name + "!"; }

    // Handles request of the form /hello/LaunchCode (Path parameter)
    @GetMapping("hello/{name}")
    public String helloWithPathParam(@PathVariable String name) { return "Hello, " + name + "!"; }

    @GetMapping("form")
    public String helloWithForm() {
        return "<html>" +
                "<body>" +
                "<form action='hello' method='post'>" + // submit a request to /hello
                "<input type='text' name='name'/>" +
                "<input type='submit' value='Greet me!'/>" +
                "</form>" +
                "</body>" +
                "</html>"; }

}
