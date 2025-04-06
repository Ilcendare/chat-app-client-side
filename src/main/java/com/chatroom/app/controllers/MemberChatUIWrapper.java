package com.chatroom.app.controllers;

import com.chatroom.app.memberdata.Message;
import com.chatroom.app.viewcontrollers.ChatController;

public class MemberChatUIWrapper {
  private ChatController memberChatUI;

  public MemberChatUIWrapper(ChatController memberChatUI) {
    if (memberChatUI != null) {
      this.memberChatUI = memberChatUI;
      ChatMessageSender.getInstance().setMemberChatUIWrapper(this);
      MembersListUpdater.getInstance().setMemberChatUIWrapper(this);

      //Send all messages in the message queue to the chat controller
      Message awaitMessage;
      while ((awaitMessage = MessageQueue.getInstance().getMessage()) != null) {
        try {
          ChatMessageSender.getInstance().sendMessage(awaitMessage);
        } catch (Exception e) {
          System.err.println(
              "MemberChatUIWrapper: Exception occured during sending message from MessageQueue");
        }
      }
    } else {
      System.err.println("MemberChatUIWrapper: the chat controller is null.");
    }
  }

  public ChatController getMemberChatUI() {
    return memberChatUI;
  }

}
