package com.example.demo2.controller;

import com.example.demo2.model.Message;
import com.example.demo2.model.Serial;
import com.example.demo2.statics.Statics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate template;
    private Thread th1;

    public MessageController(){

    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public void sendCommand(Message message) {

        if (th1 == null) {
            Serial serial = new Serial(this);
            th1 = new Thread(serial);
            th1.start();
        }

        String text = HtmlUtils.htmlEscape(message.getContent());
        Statics.command = Integer.parseInt(text);
    }

    public void sendMessage(String message){
        this.template.convertAndSend("/topic/greetings", new Message(message));
    }
}
