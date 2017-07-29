package ntu.csie.oop13spring;
//import ntu.csie.oop13spring.*;

public class POOPetPos extends POOCoordinate{
    //protected int x, y;
    public POOPetPos(int _x, int _y){
    	x = _x;
    	y = _y;
    }
    public POOPetPos(POOCoordinate i){
        this.x = i.x;
        this.y = i.y;
    }
    public boolean equals(POOCoordinate other){
    	if(this.x == other.x && this.y == other.y)
    		return true;
    	else return false;
    }
}
