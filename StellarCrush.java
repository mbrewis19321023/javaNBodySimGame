import java.awt.event.KeyEvent;
import java.awt.Font;


/* Acknowledgements/notes:
 - Some of this code based on code for Rubrica by Steve Kroon
 - Original inspiration idea for this project was IntelliVision's AstroSmash, hence the name
 */

/* Ideas for extensions/improvements:
 PRESENTATION:
 -theme your game
 -hall of fame/high score screen
 -modifiable field of view, rear-view mirror, enhance first-person display by showing extra information on screen
 -mouse control
 -autoscaling universe to keep all universe objects on screen (or making the edge of the universe repel objects)
 -better rendering in camera (better handling of objects on edges, and more accurate location rendering
 -improved gameplay graphics, including pictures/sprites/textures for game objects
 -add sounds for for various game events/music: Warning: adding both sounds and music will likely lead to major
 headaches and frustration, due to the way the StdAudio library works.  If you go down this route, you choose
 to walk the road alone...
 -full 3D graphics with 3D universe (no libraries)
 
 MECHANICS/GAMEPLAY CHANGES:
 -avoid certain other game objects rather than/in addition to riding into them
 -more interactions - missiles, auras, bombs, explosions, shields, etc.
 -more realistic physics for thrusters, inertia, friction, momentum, relativity?
 -multiple levels/lives
 -energy and hit points/health for game objects and players
 -multi-player mode (competitive/collaborative)
 -checking for impacts continuously during moves, rather than at end of each time step
 -Optimize your code to be able to deal with more objects (e.g. with a quad-tree) - document the improvement you get
 --QuadTree implementation with some of what you may want at : http://algs4.cs.princeton.edu/92search/QuadTree.java.html
 --https://github.com/phishman3579/java-algorithms-implementation/blob/master/src/com/jwetherell/algorithms/data_structures/QuadTree.java may also be useful - look at the Point Region Quadtree
 */

public class StellarCrush {
  
  // Main game class
  
  // CONSTANTS TUNED FOR GAMEPLAY EXPERIENCE
  static final int GAME_DELAY_TIME = 5000; // in-game time units between frame updates
  static final int TIME_PER_MS = 1000; // how long in-game time corresponds to a real-time millisecond
  static final double G = 6.67e-11; // gravitational constant
  static final double softE = 0.001; // softening factor to avoid division by zero calculating force for co-located objects
  static double scale = 5e10; // plotted universe size
  //Information on the title screen
  static final String by = "Michael Brewis";
  static final String play = "any";
  static final String quit = "m";
  static final String name = "Eat Me!";
  
  public static void main(String[] args) {
    
    soundControls titleClip = new Sounds("Dovahkiin_Kostya_Remix_.wav");//name of song to be played for into theme
    titleClip.play();//calls the sound object and plays it
    
    //this draws the title screen and causes all the effects. Not in its own class because of project instructions//
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    StdDraw.setCanvasSize(800,800);
    StdDraw.clear(StdDraw.BLACK);
    StdDraw.enableDoubleBuffering();//this will enable animations basically (by flashing static pics)
    StdDraw.setScale(-2,2);
    StdDraw.setPenColor(StdDraw.WHITE);
    Font titleF = new Font("Impact", Font.PLAIN, 60);
    Font nameF = new Font("Impact", Font.BOLD, 20);
    
    do{
      StdDraw.clear(StdDraw.BLACK);
      StdDraw.setFont(titleF);
      StdDraw.text(0,0.5,name);
      StdDraw.setFont(nameF);
      StdDraw.text(0,0.3,"Use the up and down arrow keys for acceleration");
      StdDraw.text(0,0.1,"Use the right and left arrow keys for turning");
      StdDraw.text(0,-0.1,"Space bar for breaks!");
      StdDraw.text(0,-0.3,"You can only eat orbs smaller than you");
      StdDraw.text(0,-0.5,"Remeber they eat back...");
      StdDraw.text(1.1,-1.6,"By: " + by);
      StdDraw.text(0.0,-0.7,"Press " +play+ " key to play!");
      StdDraw.text(-1.3,-1.6,"Press " +quit+ " key to quit!");
      StdDraw.show();
      StdDraw.pause(100);//speed of flash in miliseconds
      StdDraw.clear(StdDraw.BLACK);
      StdDraw.setFont(nameF);
       StdDraw.text(0,0.3,"Use the up and down arrow keys for acceleration");
      StdDraw.text(0,0.1,"Use the right and left arrow keys for turning");
      StdDraw.text(0,-0.1,"Space bar for breaks!");
      StdDraw.text(0,-0.3,"You can only eat orbs smaller than you");
      StdDraw.text(0,-0.5,"Remeber they eat back...");
      StdDraw.text(1.1,-1.6,"By: " + by);
      StdDraw.text(0.0,-0.7,"Press " +play+ " key to play!");
      StdDraw.text(-1.3,-1.6,"Press " +quit+ " key to quit!");
      StdDraw.show();
      StdDraw.pause(100);
      if (StdDraw.isKeyPressed(KeyEvent.VK_M)){
        System.exit(0);
      }
    }while(!StdDraw.hasNextKeyTyped());
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    ///////////////////////////////////////////////////this begins the main game loop/////////////////////////////////
    GameState gameState = new GameState();
    gameState.update(5000,10,5e10,800);//MUST BE 800 OTHERWISE DETECTION IS FAULTY 
    int endState = gameState.getState();
    int score = gameState.getTime();
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    ///////////////////////////////////////////////////displayes the end game screen/////////////////////////////////
    StdDraw.setCanvasSize(800,800);
    StdDraw.setScale(-2,2);
    StdDraw.enableDoubleBuffering();
    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setFont(titleF);
    
    if (endState == 1){
     while(!StdDraw.isKeyPressed(KeyEvent.VK_M)){
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.show();
        StdDraw.pause(100);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.text(0,0,"WELL DONE YOU ARE A WINNER");
        StdDraw.text(0,-1,"Time: " + Integer.toString(score));
        StdDraw.show();
        StdDraw.pause(100);
        titleClip.stop();
       }
    }
    
     if (endState == 2){
     while(!StdDraw.isKeyPressed(KeyEvent.VK_M)){
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.show();
        StdDraw.pause(100);
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.text(0,0,"YOU ARE A LOSER");
        StdDraw.show();
        StdDraw.pause(100);
        titleClip.stop();
       }
    }
        if (StdDraw.isKeyPressed(KeyEvent.VK_M)){
          System.exit(0);
        }
    }
  }
