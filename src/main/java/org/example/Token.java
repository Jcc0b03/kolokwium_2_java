package org.example;

import java.sql.Time;
import java.util.UUID;

public class Token {
    public String token;
    public Long timeStamp;
    public Boolean isActive;

    Token(){
        this.token = UUID.randomUUID().toString();
        this.timeStamp = System.currentTimeMillis();
        isActive = true;
    }
}
