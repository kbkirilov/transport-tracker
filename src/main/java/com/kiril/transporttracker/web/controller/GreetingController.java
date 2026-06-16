package com.kiril.transporttracker.web.controller;

import com.kiril.transporttracker.models.Greeting;
import com.kiril.transporttracker.models.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Greeting("Good Morning, Mr. " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }
}
