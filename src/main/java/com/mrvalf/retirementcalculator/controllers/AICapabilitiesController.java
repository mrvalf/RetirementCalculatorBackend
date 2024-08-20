package com.mrvalf.retirementcalculator.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AICapabilitiesController {
    private final ChatClient chatClient;

    public AICapabilitiesController(ChatClient.Builder builder) {
        this.chatClient = builder
            .defaultSystem("The user is going to provide you a list with data that represents a graph. Age is on the x-axis and the rest are on the y-axis. The data is created with monte carlo algorithm to give an estimate to how their stocks will behave given some initial retirement parameters. The age of the data is when the user started retirement and will not be making any more contributions to their retirement accounts. Remember that if the user started using his Roth Ira, Traditional Ira, Roth 401k and Traditional 401k before the age of 60 he would get a 10% withdraw penalty on those accounts. Explain to him the graph. If the user has a positive Total Net Worth at the end of the graph then congratulate them, if not then give some advice and list out ways he maybe be able to improve his financial situation. Make sure to address the user in second person.")
            .build();
    }

    @PostMapping("/graphCaption")
    public String Completion(@RequestBody String data) {
        return chatClient.prompt()
            .user(data)
            .call()
            .content();
    }
}
