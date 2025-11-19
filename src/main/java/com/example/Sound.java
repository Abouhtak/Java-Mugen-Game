package com.example;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL soundUrl[] = new URL[30];
    public Sound(){
        try {
            soundUrl[0] = new File("./src/sound/Untitled video - Made with Clipchamp").toURI().toURL();
        } catch (MalformedURLException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        soundUrl[1] = getClass().getResource("./src/sound/naruto_jutsu");
        soundUrl[2] = getClass().getResource("./src/sound/Footsteps Naruto Game Sound Effect HD");
    }
    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }

}
