package com.chatroom.app.controllers;

import java.rmi.RemoteException;

import com.chatroom.app.memberdata.Message;

public class ChatMessageSender {
  private MemberChatUIWrapper memberChatUIWrapper;

  private static class Instantiator {
    private static final ChatMessageSender INSTANCE = new ChatMessageSender();
  }

  public static ChatMessageSender getInstance() {
    return Instantiator.INSTANCE;
  }

  private ChatMessageSender() {
    memberChatUIWrapper = null;
  }

  public void setMemberChatUIWrapper(MemberChatUIWrapper memberChatUIWrapper) {
    if (memberChatUIWrapper != null) {
      this.memberChatUIWrapper = memberChatUIWrapper;
    } else {
      System.err.println("ChatMessageSender:setMemberChatUIWrapper: the member chat UI wrapper is null.");
    }
  }

  public void sendMessage(Message message) throws RemoteException {
    if (memberChatUIWrapper != null) {
      memberChatUIWrapper.getMemberChatUI().showMessage(message);
    } else {
      // store the message in a message queue
      MessageQueue.getInstance().addMessage(message);
    }
  }
}
