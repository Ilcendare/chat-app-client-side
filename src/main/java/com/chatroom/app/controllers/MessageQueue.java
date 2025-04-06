package com.chatroom.app.controllers;

import java.util.LinkedList;
import java.util.Queue;

import com.chatroom.app.memberdata.Message;

public class MessageQueue {
  private Queue<Message> messageQueue;

  private MessageQueue() {
    messageQueue = new LinkedList<>();
  }

  private static class MessageQueueHolder {
    private static final MessageQueue INSTANCE = new MessageQueue();
  }

  public static MessageQueue getInstance() {
    return MessageQueueHolder.INSTANCE;
  }

  public boolean addMessage(Message message) {
    if (message != null) {
      return messageQueue.add(message);
    }
    return false;
  }

  public Message getMessage() {
    if (!messageQueue.isEmpty()) {
      return messageQueue.poll();
    }
    return null;
  }
}
