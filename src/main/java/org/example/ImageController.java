package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

@Controller
public class ImageController {

    BufferedImage generatedImage;

    ImageController(){
        this.generatedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
        Graphics2D imageGraphics = generatedImage.createGraphics();
        imageGraphics.setColor(Color.black);
        imageGraphics.fillRect(0, 0, 512, 512);
    }

    @Autowired
    DatabaseController databaseController;

    void loadPixelsFromDatabase(){
        ArrayList<PixelRequestBody> pixelsData = databaseController.getPixelsData();

        for(PixelRequestBody pixelData : pixelsData){
            pixel(pixelData);
        }
    }

    @GetMapping("/image")
    String image(Model model){
        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();

        try{
            ImageIO.write(generatedImage, "png", imageOutputStream);
            byte imageBytes[] = imageOutputStream.toByteArray();

            model.addAttribute("image", Base64.getEncoder().encodeToString(imageBytes));

            return "image";
        }catch (IOException e){
            e.printStackTrace();
        }

        return "";
    }

    public static class PixelRequestBody {
        public String token;
        public int x, y;
        public String color;

        PixelRequestBody(){
            this.token = "";
            this.x = 0;
            this.y = 0;
            this.color = "";
        }

        PixelRequestBody(String token, int x, int y, String color){
            this.token = token;
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }

    @Autowired
    RegisterController registerController;

    @PostMapping("/pixel")
    @ResponseBody
    ResponseEntity<String> pixel(@RequestBody PixelRequestBody requestBody){
        if(registerController.checkAuth(requestBody.token)){
            if(requestBody.x >= 0 && requestBody.y > 0 && requestBody.x <= 512 && requestBody.y <= 512){
                Graphics2D imageGraphics = generatedImage.createGraphics();
                imageGraphics.setColor(new Color(Integer.parseInt(requestBody.color, 16)));
                imageGraphics.fillRect(requestBody.x, requestBody.y, 1, 1);
            }else{
                return new ResponseEntity<String>("bad request", HttpStatusCode.valueOf(400));
            }

            databaseController.addPixelData(requestBody.token, requestBody.x, requestBody.y, requestBody.color);
            return new ResponseEntity<String>("done", HttpStatusCode.valueOf(200));
        }else{
            return new ResponseEntity<String>("unauthorized", HttpStatusCode.valueOf(302));
        }
    }

    void SetPixel(PixelRequestBody requestBody){
        if(registerController.checkAuth(requestBody.token)) {
            if (requestBody.x >= 0 && requestBody.y > 0 && requestBody.x <= 512 && requestBody.y <= 512) {
                Graphics2D imageGraphics = generatedImage.createGraphics();
                imageGraphics.setColor(new Color(Integer.parseInt(requestBody.color, 16)));
                imageGraphics.fillRect(requestBody.x, requestBody.y, 1, 1);
            }
        }
    }
}
