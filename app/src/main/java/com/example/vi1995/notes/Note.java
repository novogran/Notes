package com.example.vi1995.notes;

import java.util.UUID;

public class Note {

    private UUID mId;
    private String mName;
    private String mText;

    public Note(){
        this(UUID.randomUUID());
    }

    public Note(UUID id){
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
