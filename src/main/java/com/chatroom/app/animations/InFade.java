package com.chatroom.app.animations;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class InFade implements NodeAnimator {
  FadeTransition transition = new FadeTransition();

  public InFade(Node node) {
    if (transition.getStatus() != Animation.Status.RUNNING) {
      transition.setNode(node);
      transition.setDuration(Duration.millis(150));
      transition.setCycleCount(1);
      transition.setFromValue(1);
      transition.setToValue(0.6);
      transition.setAutoReverse(false);
      transition.setInterpolator(Interpolator.EASE_OUT);
    }
  }

  public void play() {
    if (transition.getStatus() != Animation.Status.RUNNING) {
      transition.play();
    }
  }
}
