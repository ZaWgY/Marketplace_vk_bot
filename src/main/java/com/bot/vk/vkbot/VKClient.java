package com.bot.vk.vkbot;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.exceptions.*;
import com.vk.api.sdk.objects.messages.Dialog;
import com.vk.api.sdk.objects.messages.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Log4j2
@Component
public class VKClient implements IClient{

    private VkApiClient apiClient;
    private GroupActor actor;

    @Value("${ID}")
    private int groupID;

    @Value("${CLIENT_SECRET}")
    private String token;

    @PostConstruct
    public void init() {
        TransportClient trClient = HttpTransportClient.getInstance();
        this.apiClient = new VkApiClient(trClient);
        this.actor = new GroupActor(groupID, token);
    }

    @Override
    public void SendMessage(String message, int id){
        log.info("Sending message...");
        Random random = new Random();
        try {
            this.apiClient.messages().send(actor).message(message).userId(id).randomId(random.nextInt()).execute();
        } catch (ApiException | ClientException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> ReadMessages(){
        log.info("Reading message...");
        List<Message> result = new ArrayList<>();
        try {
            List<Dialog> dialogs = apiClient.messages().getDialogs(actor).unanswered1(true).execute().getItems();
            for (Dialog item: dialogs) {
                result.add(item.getMessage());
            }
        } catch (ApiException | ClientException e) {
            log.error(e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void ReplyForMessages(List<Message> messages) {
        log.info("Replying message...");
        String body;
        for (Message item: messages) {
            body = item.getBody();
            SendMessage("You tap: " + body, item.getUserId());
        }
    }

    @Override
    public int GetMembersCount() {
        log.info("Getting member count...");
        int result = 0;
        try {
            result = apiClient.groups().getMembers(actor).groupId(actor.getGroupId().toString()).execute().getItems().size();
        } catch (ApiException | ClientException e) {
            log.error(e);
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<Integer> GetMembers() {
        log.info("Getting members list...");
        List<Integer> result = null;
        try {
            result = apiClient.groups().getMembers(actor).groupId(actor.getGroupId().toString()).execute().getItems();
        } catch (ApiException | ClientException e) {
            log.error(e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void PostProduct() {
        //post
    }

    @Override
    public void DeleteProduct() {
        //delete
        //apiClient.market().getById()
    }

    @Override
    public void BanUser() {
        //ban
    }

    @Override
    public void UnBanUser() {
        //unban
    }
}