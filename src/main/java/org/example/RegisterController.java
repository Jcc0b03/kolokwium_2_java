package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RegisterController {
    ArrayList<Token> Tokens;

    RegisterController(){
        this.Tokens = new ArrayList<Token>();
    }

    @PostMapping("/register")
    public String register(){
        Token newToken = new Token();
        Tokens.add(newToken);
        return newToken.token;
    }

    @GetMapping("/tokens")
    ArrayList<Token> tokens(){
        return Tokens;
    }

    public Boolean checkAuth(String providedToken){
        for(Token tokenObject : Tokens){
            if(providedToken.equals(tokenObject.token)){
                return true;
            }
        }

        return false;
    }

    //TODO
    void saveTokensToFile(){

    }

    void loadTokens(){

    }
}
