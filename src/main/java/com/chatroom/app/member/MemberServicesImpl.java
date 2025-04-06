package com.chatroom.app.member;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.chatroom.app.memberdata.MemberServices;
import com.chatroom.app.memberdata.MemberContainer;
import com.chatroom.app.memberdata.Message;
import com.chatroom.app.controllers.MembersListUpdater;
import com.chatroom.app.controllers.ChatMessageSender;

public class MemberServicesImpl extends UnicastRemoteObject implements MemberServices {

  public MemberServicesImpl() throws RemoteException {
    super();
  }

  @Override
  public void receiveMessage(Message msg) throws RemoteException {
    if (msg != null) {
      ChatMessageSender.getInstance().sendMessage(msg);
    } else {
      System.err.println("MemberImpl: memberChat is null. Can't receive message.");
    }
  }

  @Override
  public void updateMembersList(List<MemberContainer> memberList) throws RemoteException {
    MembersListUpdater.getInstance().updateMembersList(memberList);
  }

  @Override
  public void setMemberId(int id) throws RemoteException {
    if (id != 0) {
      MemberManager.getMember().getMemberData().setId(id);
    } else {
      System.err.println("     >>>>>     MemberServicesImpl:setMemberId  >>  the ID is zero");
    }
  }
}
