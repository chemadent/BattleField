package ntu.csie.oop13spring;
//import ntu.csie.oop13spring.*;

public class POOPetCat extends POOPet{

    private POOPetPos initpos;              //pet's initial coordinate, but it will be modified when pet moves
    protected final POOPetPos getInitPos(){
        return new POOPetPos(initpos.x, initpos.y);
    }

    public POOPetCat(){
        setHP(50);
        setMP(50);
        setAGI(2);
        setName("Cat");
        initpos = new POOPetPos(0,0);
    }
    public final POOSkill getSkill(){
        return new POOCrazyScratchSkill();
    }
    public final String getSkillName(){
        return "POOCrazyScratchSkill";
    }
    /**
       act defines how the pet would choose a pet
       on the arena (including possibly itself)
       and determine a skill to be used on
       the pet
    */
    protected POOAction act(POOArena arena){
        int i = 0;
        POOPet[] allpets = arena.getAllPets();
        while(allpets[i] instanceof POOPetCat)    i++;
        POOAction action = new POOAction();
        action.skill = new POOCrazyScratchSkill();
        action.dest = allpets[i];
        return action;
    }

    /**
       move defines how the pet would want to move in an arena;
       note that the range of moving should be related to AGI
     */
    protected POOCoordinate move(POOArena arena){
        POOBattleField _arena = (POOBattleField)arena;
        switch(_arena.getDirection1()){
            case 'u':
                initpos.y -= (getAGI()-_arena.getSlowdown());       //since cat's AGI is larger than 1, it might walk by just 1 unit if necessary
                break;
            case 'd':
                initpos.y += (getAGI()-_arena.getSlowdown());
                break;
            case 'r':
                initpos.x += (getAGI()-_arena.getSlowdown());
                break;
            case 'l':
                initpos.x -= (getAGI()-_arena.getSlowdown());
                break;                              
        }
        return new POOPetPos(initpos);
    }
}
