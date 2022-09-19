package com.example.oauthtest.model;

public interface Converter<I, O> {

  O convert(I source);

}
