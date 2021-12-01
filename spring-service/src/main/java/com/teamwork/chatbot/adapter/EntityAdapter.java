package com.teamwork.chatbot.adapter;

public interface EntityAdapter<T, B> {
    public abstract B toMapper(T entity);
}
