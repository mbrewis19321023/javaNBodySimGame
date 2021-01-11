import java.awt.Color;
import java.awt.event.KeyEvent;

public class PlayerObject extends GameObject{

    private static final Color DEFAULT_COLOR = StdDraw.WHITE;
    private static final Color DEFAULT_FACING_COLOR = StdDraw.BLACK;
    private static final double DEFAULT_FOV = Math.PI/2; // field of view of player's viewport
    private static final double FOV_INCREMENT = Math.PI/150; // rotation speed
    private double orientation;
    
    public PlayerObject (Vector r, Vector v, double mass, double size, Color colour, double orientation){
      super(r,v,mass,size,colour,0);
      this.orientation = orientation; 
    }
    
    
    @Override
    public void drawActor() {
      Vector r = this.getR();
      Double size = this.getSize();
      Color colour = this.getColour();
      
      StdDraw.setPenColor(colour);
      StdDraw.setPenRadius(size);
      if (r != null){
        StdDraw.point(r.cartesian(0), r.cartesian(1));
      }
      
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(size*0.8);
      if (r != null){
        StdDraw.point(r.cartesian(0)+(Math.cos(orientation)*1e9), r.cartesian(1) + (Math.sin(orientation)*1e9));
      }
    }
    
    
    
    public double getOrientation(){
      return orientation;
    }
    
    
    
    
    
    
    
    
    
    
    
// highlight objects below this mass
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    void processCommand(int delay,Camera cam) {
      // Process keys applying to the player
      // Retrieve 
      if (cam != null) {
        // No commands if no draw canvas to retrieve them from!
        Draw dr = cam.getDraw();
        if (dr != null) {
          //makes you go forwarrd
          if (dr.isKeyPressed(KeyEvent.VK_UP)){
            Vector v = this.getV();
            Vector plus = new Vector(new double[]{(100*Math.cos(orientation)),(100*Math.sin(orientation))});
            v = v.plus(plus);
            this.setV(v);
          }
          //makes you go backward
          if (dr.isKeyPressed(KeyEvent.VK_DOWN)){
            Vector v = this.getV();
            Vector plus = new Vector(new double[]{(-100*Math.cos(orientation)),(-100*Math.sin(orientation))});
            v = v.plus(plus);
            this.setV(v);
          }
          //makes you turn left
          if (dr.isKeyPressed(KeyEvent.VK_LEFT)){
            orientation = this.orientation + 2*FOV_INCREMENT;
          }
          //makes you turn right
          if (dr.isKeyPressed(KeyEvent.VK_RIGHT)){
            orientation = this.orientation - 2*FOV_INCREMENT; 
          }
          //stops you dead
          if (dr.isKeyPressed(KeyEvent.VK_SPACE)){
            this.setV(new Vector(new double[]{0,0}));
          }
          //boosts you
//           if (dr.isKeyPressed(KeyEvent.VK_W)){
//            Vector r = this.getR();
//            Vector plus = new Vector(new double[]{(1e8*Math.cos(orientation)),(1e8*Math.sin(orientation))});
//            r = r.plus(plus);
//            this.setR(r);
//            this.setV(new Vector(new double[]{0,0}));
//          }
           //exit the game if m is pressed on actor
           if (dr.isKeyPressed(KeyEvent.VK_M)){
             System.exit(0);
          }
           
           if (dr.isKeyPressed(KeyEvent.VK_E)){
             System.out.println((this.getTheta()*(180/Math.PI)));
           }
        }
      }
    }
}
