package com.chatroom.app.member;

import com.chatroom.app.memberdata.MemberData;
import com.chatroom.app.memberdata.MemberContainer;
import com.chatroom.app.memberdata.MemberServices;
import com.chatroom.app.memberdata.Password;

import java.rmi.RemoteException;

public class MemberFactory {
  // Create a new member with a username and password
  public static MemberContainer createMember(String username, String password)
      throws RemoteException, IllegalArgumentException {
    return (new MemberBuilder()).setMemberName(username).setMemberPassword(password).build();
  }

  public static class MemberBuilder {
    private String memberName;
    private Password memberPassword;
    private MemberData memberData;

    public MemberBuilder setMemberName(String username) {
      if (username != null) {
        this.memberName = username;
      } else {
        System.err.println("MemberBuilder: the username is null.");
      }
      return this;
    }

    public MemberBuilder setMemberPassword(String plainPassword) throws RemoteException, IllegalArgumentException {
      if (plainPassword != null) {
        this.memberPassword = new Password(plainPassword, false);
      } else {
        System.err.println("MemberBuilder: the password is null.");
      }
      return this;
    }

    public MemberContainer build() throws RemoteException {
      // Create a member data object. The member id will be set later by the server,
      // it's initialized with 0 for now.
      this.memberData = new MemberData(0, memberName, memberPassword);
      MemberServices memberServices = new MemberServicesImpl();
      return new MemberContainer(memberServices, memberData);
    }
  }
}
