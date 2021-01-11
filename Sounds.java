import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

public class Sounds implements soundControls{
  private Clip dova;
  
  public Sounds (String filename) {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());
      dova = AudioSystem.getClip();
      dova.open(audioInputStream);
    }
    catch(Exception ex) {
      System.out.println("Error with playing sound.");
      ex.printStackTrace();
    }
  }
  
  public void play(){
    dova.setFramePosition(0);  
    dova.start();
  }
  
  public void stop(){
    dova.stop();
  }
  
  
}

