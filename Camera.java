
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


class Camera{
  // Virtual camera - uses a plane one unit away from the focal point
  // For ease of use, this simply locates where the centre of the object is, and renders it if that is in the field of view.
  // Further, the correct rendering is approximated by a circle centred on the projected centre point.
  
  //private final IViewPort holder; // Object from whose perspective the first-person view is drawn
  private final Draw dr; // Canvas on which to draw
  private double FOV; // field of view of camera
  private Vector r;
  
  Camera(double FOV, Draw dr, Vector r) {
    this.dr = dr;
    this.r = r;
  }
  
  void render(GameObject asteroid, PlayerObject player) {
    
    double R,r,d,phi,renderW;
    double s = 500;
    double angle1 = 0;
    double angle2 = 0;
    double renderCheck = 0;
    PlayerObject temp =(PlayerObject)player;
    for (int i = 1; i < 2; i++){
      R = 200;
      r = asteroid.getSize();
      d = player.getR().distanceTo(asteroid.getR()); 
      phi = Math.atan((r)/(d));
      renderW = phi*R;
      if (((asteroid.getR().cartesian(1) - player.getR().cartesian(1)) > 0) && ((asteroid.getR().cartesian(0) -(player.getR().cartesian(0)))>0)){////333333333333333333
        angle1 =(Math.PI) + Math.atan((asteroid.getR().cartesian(1) -(player.getR().cartesian(1) ))/((asteroid.getR().cartesian(0) -(player.getR().cartesian(0)))));
      }
      else if (((asteroid.getR().cartesian(1) - player.getR().cartesian(1)) > 0) && ((asteroid.getR().cartesian(0) -(player.getR().cartesian(0)))<0)){//444444444444444444444
       angle1 = (Math.PI*2) + Math.atan((asteroid.getR().cartesian(1) -(player.getR().cartesian(1) ))/((asteroid.getR().cartesian(0) -(player.getR().cartesian(0)))));
      }
      else if (((asteroid.getR().cartesian(1) - player.getR().cartesian(1)) < 0) && ((asteroid.getR().cartesian(0) -(player.getR().cartesian(0)))<0)){//111111111111111111111111
        angle1 = Math.atan((asteroid.getR().cartesian(1) -(player.getR().cartesian(1) ))/((asteroid.getR().cartesian(0) -(player.getR().cartesian(0)))));
      }
      else if (((asteroid.getR().cartesian(1) - player.getR().cartesian(1)) < 0) && ((asteroid.getR().cartesian(0) -(player.getR().cartesian(0)))>0)){///////22222222222222222
        angle1 =(Math.PI) + Math.atan((asteroid.getR().cartesian(1) -(player.getR().cartesian(1) ))/((asteroid.getR().cartesian(0) -(player.getR().cartesian(0)))));
      }
      temp = (PlayerObject)player;
      angle2 =Math.abs((temp.getOrientation() - angle1)) % Math.PI;
      renderCheck = Math.abs((temp.getOrientation() - angle1)/Math.PI) % 2;
      s = R*angle2;
      dr.setPenColor(asteroid.getColour());
      dr.setPenRadius(renderW*1e8);
      if((s < 150 || s > 450) && ((renderCheck) < 1.2 && (renderCheck) > 0.7 )){
        dr.point(s-314.1592654,300);
        dr.point(s+314.1592654,300);
      }
      else if((s > 150 && s < 450) && ((renderCheck) > 1.2 || (renderCheck) < 0.7 )){
        dr.point(s-314.1592654,900);
        dr.point(s+314.1592654,900);
        
      }
      else if (true){
        dr.point(s-314.1592654,900);
        dr.point(s+314.1592654,900);
      }
  
    }
  }
  
  Draw getDraw(){
    return dr;
  }
  
}

