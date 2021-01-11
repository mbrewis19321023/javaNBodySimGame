import java.awt.Color;

public class GameObjectLibrary {
// Class for defining various game objects, and putting them together to create content
// for the game world.  Default assumption is objects face in the direction of their velocity, and are spherical.

    // UNIVERSE CONSTANTS - TUNED BY HAND FOR RANDOM GENERATION
    private static final double ASTEROID_RADIUS = 0.5; // Location of asteroid belt for random initialization
    private static final double ASTEROID_WIDTH = 0.2; // Width of asteroid belt
    private static final double ASTEROID_MIN_MASS = 1E24;
    private static final double ASTEROID_MAX_MASS = 1E26;
    private static final double PLAYER_MASS = 1E25;
    
    ////constructs a single actor with random properties///
    public static GameObject actorMake (double universeSize){
      
      //sets the position//
      double rad = (Math.random() * (universeSize/2*0.7 - universeSize/2*0.5 ) + universeSize/2*0.5);
      double theta = (Math.random() * (2*Math.PI));
      double rx = universeSize/2 + Math.cos(theta)*rad;
      double ry = universeSize/2 + Math.sin(theta)*rad;
      double[] rv = { rx, ry };
      Vector r = new Vector(rv);
      ///this handles the velocity of the actor//
      double vx =  0;
      double vy = 0;
      double[] vv = { vx, vy };
      Vector v = new Vector(vv);
      //sets the mass//
      double m =  (Math.random() * ((1E26 - 1E25) + 1e25));
      //sets the size according to mass//
      double s = m / 1e27;
      //sets the colour//
      int red =(int) (Math.random()*130) + 125;
      int green =(int) (Math.random()*130) + 125;
      int blue = (int)(Math.random()*130) + 125;
      Color c = new Color(red,green,blue);
      GameObject actor = new GameObject (r,v,m,s,c,theta);
      return actor;
    }
    
    ////constructs a single player///
    public static PlayerObject playerMake (){  
      //sets the position//
      Vector r = new Vector(new double[]{2.5e10,2.5e10});
      ///this handles the velocity of the actor//
      double vx =  0;
      double vy = 0;
      double[] vv = { vx, vy };
      Vector v = new Vector(vv);
      //sets the mass//
      double m = PLAYER_MASS; 
      //sets the size according to mass//
      double s = m*7 / 1e27;
      //sets the colour//
      int red = 255;
      int green = 0;
      int blue = 255;
      Color c = new Color(red,green,blue);
      double orientation = Math.PI/2;
      PlayerObject player = new PlayerObject (r,v,m,s,c,orientation);
      return player;
    }
    
    
    
    
    
    
//      Iterator<GameObject> iter = asteroids.iterator();
//      while (iter.hasNext()) {
//        GameObject iterObj = iter.next();
//        System.out.println(iterObj.getPosition());
//      }
    }
    
    
      
      
      
