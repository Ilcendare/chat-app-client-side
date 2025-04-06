package com.chatroom.app.animations;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TranslateButton implements NodeAnimator {
  TranslateTransition transition = new TranslateTransition();

  public TranslateButton(Node node) {
    if (transition.getStatus() != Animation.Status.RUNNING) {
      transition.setNode(node);
      transition.setDuration(Duration.millis(150));
      transition.setByY(3);
      transition.setCycleCount(2);
      transition.setAutoReverse(true);
      transition.setInterpolator(Interpolator.EASE_OUT);
    }
  }

  public void play() {
    if (transition.getStatus() != Animation.Status.RUNNING) {
      transition.play();
    }
  }
}
