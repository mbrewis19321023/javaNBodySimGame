import java.awt.Color;


public class GameObject {
  
  private Vector r; //position of particle
  private Vector v; // velocity of particle
  private double mass; // mass of particle
  private double size; //unseen size of the particle due to mass
  private Color colour;//colour of the actor
  private boolean doIExist;
  private double theta;
  
  public GameObject (Vector r, Vector v, double mass, double size, Color colour, double theta){
    this.r = r;
    this.v = v;
    this.mass = mass;
    this.size = size;
    this.colour = colour;
    this.doIExist = true;
    this.theta = theta;
  }
  
  
  public void move(Vector f, double dt) {
    Vector a = f.times(1/mass);
    v = v.plus(a.times(dt));
    if (r != null){
      r = r.plus(v.times(dt));
      if (((this.getR().cartesian(1) - 2.5E10) > 0) && ((this.getR().cartesian(0) - 2.5E10) > 0)){ ////1st quad
        theta = Math.atan((this.getR().cartesian(1) - 2.5E10)/((this.getR().cartesian(0) - 2.5E10)));
      }
      else if (((this.getR().cartesian(1) - 2.5E10) < 0) && ((this.getR().cartesian(0) - 2.5E10) > 0)){//4th quad
        theta = (Math.PI*2) + Math.atan((this.getR().cartesian(1) - 2.5E10)/((this.getR().cartesian(0) - 2.5E10)));
      }
      else if (((this.getR().cartesian(1) - 2.5E10) < 0) && ((this.getR().cartesian(0) - 2.5E10) < 0)){//3rd quad
        theta = (Math.PI) + Math.atan((this.getR().cartesian(1) - 2.5E10)/((this.getR().cartesian(0) - 2.5E10)));
      }
      else if (((this.getR().cartesian(1) - 2.5E10) > 0) && ((this.getR().cartesian(0) - 2.5E10) < 0)){//2nd quad
        theta = (Math.PI) + Math.atan((this.getR().cartesian(1) - 2.5E10)/((this.getR().cartesian(0) - 2.5E10)));
      }
    }
  }
  
  public Vector forceFrom(GameObject that) {
    double G = 6.67e-11;
//    System.out.println("that,r:" + that.r);
//    System.out.println("this.r:" + this.r);
    Vector delta;
    /////////////////this will identify that a obj is being compared
    //to itself and then simply times its position with itself//////
    if (that.r != this.r){
      delta = that.r.minus(this.r);
    }else {
      delta = (this.r).times(1.000000000000000001);
    }
    //////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////
//    System.out.println("delta:" + delta);
    double dist = delta.magnitude();
//    System.out.println("dist:" + dist);
    double F = (G * this.mass * that.mass) / (dist * dist);
    return VectorUtil.direction(delta).times(F);
    //return delta.direction().times(F);
  }
 
  
/////////////////////////WROKS FOR TOP DOWN DRAW///////////////////// 
  public void drawActor() {
    StdDraw.setPenColor(colour);
    StdDraw.setPenRadius(this.size);
    if (r != null){
      StdDraw.point(r.cartesian(0), r.cartesian(1));
    }
  }
 ///////////////////////////////////////////////////////////
  
  
  public Vector getV(){
    return v;
  }
  
  public Vector getR(){
    return r;
  }
  
  
  public double getMass(){
    return mass;
  }
  
   public void addMass(double addMass){
    mass = mass +addMass;
  }
   
   public boolean getStatus(){
    return doIExist;
  }
    public void setStatus(boolean b){
    doIExist = b;
  }
  
  public double getSize(){
    return size;
  }
  
  public void addSize(double addSize){
    size = size + (addSize*0.2);
  }
  
  public Color getColour(){
    return colour;
  }
  
  public void setV(Vector vel){
    v = vel;
  }
  
  
   public void setR(Vector pos){
    r = pos;
  }
  
   public void deleteMe(){
    mass = 0.00000001;
    size = 0.00000001;
    colour = StdDraw.BLACK;
    doIExist = false;
    r = VectorUtil.FARAWAY.minus(r);
  }
   
   public double getTheta(){
     return theta;
   }
   
  
  
  
  
  
}



