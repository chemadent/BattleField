package ntu.csie.oop13spring;
//import ntu.csie.oop13spring.*;

class POOCrazyScratchSkill extends POOSkill{
	public final int mpRequire = 3;                //when pets implement this skill, their mp points would be deducted
    public void act(POOPet target, POOPet myself){
    	
        int hp = target.getHP();
        if (hp >= 10)
            target.setHP(hp - 10);
        else target.setHP(0);

        int mp = myself.getMP();
        if(mp >= mpRequire)
        	myself.setMP(mp - mpRequire);
        else myself.setMP(0);
    }
    public void act(POOPet pet){
    }
}