package com.example.bikash_messenger.Models;

public class Upload {
    private String Name;
    private String ImageUri;

    public Upload() {

    }

    public Upload(String name, String imageUri) {
        if(name.trim().equals("")){
            name="No Name";
        }
        Name = name;
        ImageUri = imageUri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }
}
