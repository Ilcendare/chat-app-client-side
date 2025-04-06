package com.chatroom.app.controllers;

import java.util.List;

import com.chatroom.app.memberdata.MemberContainer;

public class MembersListUpdater {
  private MemberChatUIWrapper memberChatUIWrapper;

  private MembersListUpdater() {
    memberChatUIWrapper = null;
  }

  private static class Instantiator {
    private static final MembersListUpdater INSTANCE = new MembersListUpdater();
  }

  public static MembersListUpdater getInstance() {
    return Instantiator.INSTANCE;
  }

  public void setMemberChatUIWrapper(MemberChatUIWrapper memberChatUIWrapper) {
    if (memberChatUIWrapper != null) {
      this.memberChatUIWrapper = memberChatUIWrapper;
    } else {
      System.err.println("MembersListUpdater: the member chat UI wrapper is null.");
    }
  }

  public void updateMembersList(List<MemberContainer> memberList) {
    if (memberChatUIWrapper != null) {
      memberChatUIWrapper.getMemberChatUI().updateMembersList(memberList);
    }
  }
}
