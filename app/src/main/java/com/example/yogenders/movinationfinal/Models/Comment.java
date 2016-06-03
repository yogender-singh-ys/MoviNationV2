package com.example.yogenders.movinationfinal.Models;


public class Comment {

    private String author;
    private String content;

    public Comment(String author,String content) {
        this.content = content;
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getContent()
    {
        return content;
    }

}
