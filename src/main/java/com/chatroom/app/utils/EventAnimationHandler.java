package com.chatroom.app.utils;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import com.chatroom.app.animations.NodeAnimator;
import java.util.function.Consumer;

/*
 * 
 * This class is used to handle the animation of the nodes.
 * The user is responsible for creating the animation class that implements the NodeAnimator interface 
 * for the nodes, and then pass it to the constructor of this class.
 * The user is also responsible for passing the event handler and the node he wants to apply the animation to.
 */
public class EventAnimationHandler {
  NodeAnimator animator;

  public EventAnimationHandler(NodeAnimator animator, Consumer<EventHandler<MouseEvent>> eventHandler) {
    this.animator = animator;

    // Set animation for the event handler
    eventHandler.accept(event -> {
      animator.play();
    });
  }
}
