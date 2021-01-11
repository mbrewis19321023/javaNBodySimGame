import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.awt.Font;


public class GameState {
  // Class representing the game state and implementing main game loop update step.
  
  private int gameState = 0; //1 means that we won. 2 means that we lost   
  private HashMap<Integer , GameObject> asteroid = new HashMap<Integer ,GameObject>();
  private HashMap<Integer , GameObject> temp = new HashMap<Integer ,GameObject>();
  private final PlayerObject player = GameObjectLibrary.playerMake();
  private GameObject splitTwin = null;
  private TreeMap<Double , GameObject> asteroidTree = new TreeMap<Double ,GameObject>();
  int score = 0;
  
  
  void update(int delay, int actorsN, double universeSize, int canvasSize) {
    
    //adds acotrs to a collection//
    asteroid.put(0,player);
    for (int i = 1; i < actorsN + 1; i++){
      GameObject newAsteroid = GameObjectLibrary.actorMake(universeSize);
      asteroid.put(i,newAsteroid);
    }
    
    int countRemoved = 0;
    
    ////////////clears the top down title screen//////////////
    StdDraw.setCanvasSize(canvasSize,canvasSize);
    StdDraw.setXscale(0, 5e10);
    StdDraw.setYscale(0, 5e10);
    StdDraw.clear(StdDraw.BLACK);
    
   //////////creates the camera canvas/////////////////////////
    Draw firstPerson = new Draw();
    firstPerson.setCanvasSize(canvasSize,canvasSize);
    firstPerson.setXscale(150, 450);
    firstPerson.setYscale(150, 450);
    firstPerson.setLocationOnScreen(810,1);
    Camera cam = new Camera(Math.PI/2,firstPerson, player.getR());
   
    //////////////////////////////main game loop/////////////////////////////
    while (!StdDraw.isKeyPressed(KeyEvent.VK_M) && gameState == 0){
      score += 500;
      StdDraw.enableDoubleBuffering();
      StdDraw.clear(StdDraw.BLACK);
      
     ////////////////////////sorts the hash map according to distance sothat first person renders well////////////
      asteroidTree.clear();//clear previous tree
      double r = 0;//distance between player and object
      for (int i = 1; i < asteroid.size() ; i++){
          r = asteroid.get(0).getR().distanceTo(asteroid.get(i).getR());
          asteroidTree.put(r,asteroid.get(i));
      }//gets the values of distance and uses them as keys to the map
      
      
      Set<Entry<Double , GameObject>>sorted = asteroidTree.entrySet();//returns a set version of the tree with entries in acsending order
      
      int c = asteroid.size() -1;
      asteroid.clear();
      asteroid.put(0,player);
      for(Entry<Double , GameObject> sort : sorted){
            asteroid.put(c,sort.getValue());
            c--;
        }//puts the sorted entries back into the original hashmap so the program can use them well
      //////////////////////////////////////////////////////////////////////////////////////
      


     ////////////////creates the force vectors for each game object/////////////////
      Vector[] force = new Vector [asteroid.size()];
      for (int i = 0; i < asteroid.size(); i++){
        force[i] = new Vector(new double[2]);
      }
      
      /////////////calculates immediate affects of a body due to surroundungs/////////
      for (int i = 0; i < asteroid.size();i++){
        for (int j = 0; j < asteroid.size();j++){
          if (asteroid.get(i) != null && asteroid.get(j) != null){
            force[i] = force[i].minus(asteroid.get(j).forceFrom(asteroid.get(i)));
          }
        }
      }
      
      /////////////tests to see if the edges have been reached and bounce/////////
      for (int i = 0; i < asteroid.size();i++){
        if (asteroid.get(i).getR().cartesian(0) + asteroid.get(i).getSize()*(canvasSize*2E7)> 5e10 ||
            asteroid.get(i).getR().cartesian(0) - asteroid.get(i).getSize()*(canvasSize*2E7) < 0     ){
          double xVel = -1 * asteroid.get(i).getV().cartesian(0);
          double yVel = asteroid.get(i).getV().cartesian(1);
          Vector newVel = new Vector (new double[]{xVel,yVel});
          asteroid.get(i).setV(newVel);
        }
        else if (asteroid.get(i).getR().cartesian(1) + asteroid.get(i).getSize()*(canvasSize*2E7) > 5e10 || 
            asteroid.get(i).getR().cartesian(1) - asteroid.get(i).getSize()*(canvasSize*2E7) < 0       ){
          double xVel = asteroid.get(i).getV().cartesian(0);
          double yVel = -1 * asteroid.get(i).getV().cartesian(1);
          Vector newVel = new Vector (new double[]{xVel,yVel});
          asteroid.get(i).setV(newVel);
        }
      }
      
      
      //////updates the position of the bodies according to what was calculated////////
      for (int i = 0; i < asteroid.size(); i++){
        if (asteroid.get(i) != null){
          asteroid.get(i).move(force[i], delay);
        }
      } 
      
      ///////////////////draw the actors on the canvas////////////////////
      firstPerson.enableDoubleBuffering();
      for (int i = 0; i < asteroid.size(); i++){
        if (asteroid.get(i) != null){
          asteroid.get(i).drawActor();
          if(i != 0){
          cam.render(asteroid.get(i),(PlayerObject)asteroid.get(0));
          }
        }
      }
      StdDraw.setPenColor(StdDraw.WHITE);
      Font timeF = new Font("Impact", Font.PLAIN, 60);
      StdDraw.setFont(timeF);
      StdDraw.text(2.5e10,4.7e10,"TIME: " + score/80000);
      firstPerson.show();
      firstPerson.clear(StdDraw.BLACK);
      firstPerson.setPenColor(StdDraw.WHITE);
      firstPerson.setPenRadius(0.001);
      firstPerson.line(275,300,325,300);
      firstPerson.line(300,275,300,325);
      /////////////////////////////////////////////////////
      
      //////////////////////////////collision detection//////////////////////////////
      double dist = 0;//variable for determining the distances between objects
      for (int i = 0; i < asteroid.size();i++){
        for (int j = 0; j < asteroid.size();j++){
          if (asteroid.get(i) != null && asteroid.get(j) != null){
            dist = asteroid.get(j).getR().distanceTo(asteroid.get(i).getR()); 
          }
          if (asteroid.get(i) != null && asteroid.get(j) != null){
            if ((dist <= (asteroid.get(j).getSize() + asteroid.get(i).getSize())*(canvasSize*2E7)) && (dist != 0)){
              if (asteroid.get(i) == asteroid.get(0) && asteroid.get(j).getStatus() == true){
                if (asteroid.get(0).getSize() > asteroid.get(j).getSize()){
                  asteroid.get(0).addSize(asteroid.get(j).getSize());
                  asteroid.get(j).deleteMe();
                  countRemoved++;
                  System.out.println(countRemoved);
                  System.out.println(asteroid.get(j).getTheta());
                }else{
                  gameState = 2;//lose condition
                  firstPerson.clear();
                  break;
                }
              } else if (asteroid.get(i).getStatus() == true && asteroid.get(j).getStatus() == true){
                double random = Math.random();
                if (random >= 0.2 ){
                  if (asteroid.get(j).getSize() > asteroid.get(i).getSize()){
                    asteroid.get(j).addSize(asteroid.get(i).getSize());
                    asteroid.get(i).deleteMe();
                    countRemoved++;
                    System.out.println("1");
                  }else if (asteroid.get(i).getSize() > asteroid.get(j).getSize()){
                    asteroid.get(i).addSize(asteroid.get(j).getSize());
                    asteroid.get(j).deleteMe();
                    countRemoved++;
                    System.out.println("2");
                  } 
                }
                if (random < 0.2){
                  GameObject split = new GameObject(asteroid.get(j).getR().plus(new Vector(new double[]{asteroid.get(j).getSize() - 2e9, asteroid.get(j).getSize() - 2e9})),  asteroid.get(j).getV().plus(new Vector(new double[]{asteroid.get(j).getV().cartesian(0)*2, asteroid.get(j).getV().cartesian(1)*2})), asteroid.get(j).getMass()/1.5, asteroid.get(j).getSize()/1.5,  asteroid.get(j).getColour(),asteroid.get(j).getTheta());
                  asteroid.remove(j);
                  asteroid.put(j, split);
                  splitTwin = new GameObject(asteroid.get(j).getR().plus(new Vector(new double[]{asteroid.get(j).getSize() + 2e9, asteroid.get(j).getSize() + 2e9})),  asteroid.get(j).getV().plus(new Vector(new double[]{asteroid.get(j).getV().cartesian(0)*-2, asteroid.get(j).getV().cartesian(1)*-2})), asteroid.get(j).getMass(), asteroid.get(j).getSize(),  asteroid.get(j).getColour(),asteroid.get(j).getTheta());
                  asteroid.put(asteroid.size() ,splitTwin);
                }
              }
            }
          }
        }
      }
      
      /////////////winner/////////
      if (countRemoved == (asteroid.size() - 1)){
       gameState = 1;//win condition
        firstPerson.clear();
      }
      
      
      ///////////////////////////updates player according to keys pressed//////////////////
      PlayerObject temp =   (PlayerObject)asteroid.get(0);
      temp.processCommand(5000,cam);
      

      
 
      //////////////////////////////////////////////////////
      StdDraw.show(); 
    }//this is the end of the ganme loop
    if (StdDraw.isKeyPressed(KeyEvent.VK_M)){
      System.exit(0);
    }
  }
  

  /////////////////////////test the class//////////////////
  public static void main(String[] args) {
    GameState gameState = new GameState();
    gameState.update(5000,20,5e10,800); 
  }
  ////////////////////////////////////////////////////////
  
  public int getState(){
    return gameState;
  }
  
  public int getTime(){
    return score/80000;
  }
}
