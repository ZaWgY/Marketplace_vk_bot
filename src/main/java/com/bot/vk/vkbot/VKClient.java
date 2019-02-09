package com.bot.vk.vkbot;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.exceptions.*;
import com.vk.api.sdk.objects.messages.Dialog;
import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Random random = new Random();
        try {
            this.apiClient.messages().send(actor).message(message).userId(id).randomId(random.nextInt()).execute();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> ReadMessages(){
        List<Message> result = new ArrayList<Message>();
        try {
            List<Dialog> dialogs = apiClient.messages().getDialogs(actor).unanswered1(true).execute().getItems();
            for (Dialog item: dialogs) {
                result.add(item.getMessage());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void ReplyForMessages(List<Message> messages) {
        String body;
        for (Message item: messages) {
            body = item.getBody();
            SendMessage("You tap: " + body, item.getUserId());
        }
    }

    @Override
    public int GetMembersCount() {
        int result = 0;
        try {
            result = apiClient.groups().getMembers(actor).groupId(actor.getGroupId().toString()).execute().getItems().size();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public List<Integer> GetMembers() {
        List<Integer> result = null;
        try {
            result = apiClient.groups().getMembers(actor).groupId(actor.getGroupId().toString()).execute().getItems();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (ClientException e) {
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