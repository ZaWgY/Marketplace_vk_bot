package com.bot.vk.vkbot;

import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VKClientRunner {

    @Autowired
    private VKClient client;

    @Scheduled(fixedDelay = 100)
    public void scheduleFixedDelayTask() {
        System.out.print("It work!");
        List<Message> messages = client.ReadMessages();
        if(!messages.isEmpty()) client.ReplyForMessages(messages);
    }
}