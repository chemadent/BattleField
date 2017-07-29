package ntu.csie.oop13spring;
//import ntu.csie.oop13spring.*;

class POOCrazyBiteSkill extends POOSkill{
	public final int mpRequire = 5;                //when pets implement this skill, their mp points would be deducted
    public void act(POOPet target, POOPet myself){
    	
        int hp = target.getHP();
        if (hp >= 12)
            target.setHP(hp - 12);
        else target.setHP(0);

        int mp = myself.getMP();
        if(mp >= mpRequire)
        	myself.setMP(mp - mpRequire);
        else myself.setMP(0);
    }
    public void act(POOPet pet){
    }
}