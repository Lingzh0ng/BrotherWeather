package com.wearapay.brotherweather.exception;

public class NetworkException extends RuntimeException {
  public NetworkException(String detailMessage) {
    super(detailMessage);
  }
}
