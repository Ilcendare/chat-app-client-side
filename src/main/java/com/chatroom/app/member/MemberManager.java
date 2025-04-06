package com.chatroom.app.member;

import com.chatroom.app.memberdata.MemberContainer;
import com.chatroom.app.server.ServerConnectionHandler;
import java.rmi.RemoteException;

public class MemberManager {
  private static MemberContainer member;
  private static boolean isCreated = false;
  private static boolean isLoggedIn = false;

  public static void createMember(String username, String password) throws RemoteException, IllegalArgumentException {
    member = MemberFactory.createMember(username, password);
    isCreated = true;
  }

  public static boolean registerMember() throws RemoteException {
    if (member != null) {
      return ServerConnectionHandler.getInstance().getServer().register(member);
    }
    return false;
  }

  public static boolean loginMember() throws RemoteException {
    if (member != null) {
      if (ServerConnectionHandler.getInstance().getServer().login(member)) {
        isLoggedIn = true;
        return true;
      }
    } else {
      System.err.println("     >>>>>    MembersManger:login  >>  the member is null.");
    }
    return false;
  }

  public static void logoutMember() throws RemoteException {
    if (member != null) {
      ServerConnectionHandler.getInstance().getServer().logout(member);
      clearMember();
    } else {
      System.err.println("     >>>>>    MembersManger:logout  >>  the member is null.");

    }
  }

  public static MemberContainer getMember() {
    return member;
  }

  public static void clearMember() {
    member = null;
    isCreated = false;
    isLoggedIn = false;
  }

  public static boolean isCreated() {
    return isCreated;
  }

  public static boolean isLoggedIn() {
    return isLoggedIn;
  }

  public static void clearIsCreatedFlag() {
    isCreated = false;
  }
}
