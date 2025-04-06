package com.chatroom.app.server;

public class MyServer implements ServerAddressHolder {
  private String serverAddress;
  private int serverPort;

  public MyServer() {
    this.serverAddress = "192.168.100.159";
    this.serverPort = 1099;
  }

  @Override
  public String getServerAddress() {
    return serverAddress;
  }

  @Override
  public int getServerPort() {
    return serverPort;
  }
}
