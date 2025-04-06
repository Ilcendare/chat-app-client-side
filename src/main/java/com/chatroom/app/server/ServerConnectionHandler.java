package com.chatroom.app.server;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.chatroom.server.services.interfaces.ChatServer;

public class ServerConnectionHandler {
  private ChatServer server = null;
  private ServerAddressHolder serverAddressHolder;
  private boolean isConnected = false;

  private ServerConnectionHandler() {
  }

  private static class ServerConnectionHandlerInstantiator {
    private static final ServerConnectionHandler INSTANCE = new ServerConnectionHandler();
  }

  public static ServerConnectionHandler getInstance() {
    return ServerConnectionHandlerInstantiator.INSTANCE;
  }

  public void setServerAddressHolder(ServerAddressHolder serverAddressHolder) {
    if (serverAddressHolder == null) {
      throw new IllegalArgumentException("Server address holder cannot be null");
    }
    this.serverAddressHolder = serverAddressHolder;
  }

  public boolean connectServer() throws RemoteException, NotBoundException, AccessException {
    String hostName = serverAddressHolder.getServerAddress();
    int portNumber = serverAddressHolder.getServerPort();
    Registry reg = LocateRegistry.getRegistry(hostName, portNumber);
    server = (ChatServer) reg.lookup("chatroom-service");
    isConnected = true;
    return true;
  }

  public void disconnectServer() {
    server = null;
    isConnected = false;
  }

  public ChatServer getServer() {
    return server;
  }

  public boolean getServerStatus() {
    return isConnected;
  }
}
