package com.bot.vk.vkbot;

import com.vk.api.sdk.objects.messages.Message;
import java.util.List;

public interface IClient {

    void SendMessage(String message, int id); //+

    List<Message> ReadMessages(); //+

    void ReplyForMessages(List<Message> messages); //?

    int GetMembersCount(); //+

    List<Integer> GetMembers(); //+

    void PostProduct();

    void DeleteProduct();

    void BanUser();

    void UnBanUser();

    //need to complete
}
