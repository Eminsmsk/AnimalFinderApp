package com.example.animalfinder;

public class Animal {
    private String imageName;
    private String animalName;
    private String soundName;

    public Animal( String animalName, String imageName, String soundName) {
        this.imageName = imageName;
        this.animalName = animalName;
        this.soundName = soundName;
    }

    public Animal(String animalName) {
        this.animalName = animalName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }
}
