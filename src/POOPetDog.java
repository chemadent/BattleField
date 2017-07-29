package ntu.csie.oop13spring;
//import ntu.csie.oop13spring.*;

public class POOPetDog extends POOPet{
    //private int HP, MP, AGI;
    //private String name;

    private POOPetPos initpos;                      //pet's initial coordinate, but it will be modified when pet moves
    protected final POOPetPos getInitPos(){
        return new POOPetPos(initpos.x, initpos.y);
    }

    public POOPetDog(){
        setHP(50);
        setMP(50);
        setAGI(1);
        setName("Dog");
        initpos = new POOPetPos(4,4);
    }
    public final POOSkill getSkill(){
        return new POOCrazyBiteSkill();
    }
    public final String getSkillName(){
        return "POOCrazyBiteSkill";
    }
     /*   
    protected final String getName();   
    protected final int getAGI();
    protected final int getMP();  
    protected final int getHP();
    */
    /**
       act defines how the pet would choose a pet
       on the arena (including possibly itself)
       and determine a skill to be used on
       the pet
    */
    protected POOAction act(POOArena arena){
        int i = 0;
        POOPet[] allpets = arena.getAllPets();
        while(allpets[i] instanceof POOPetDog)    i++;
        POOAction action = new POOAction();
        action.skill = new POOCrazyBiteSkill();
        action.dest = allpets[i];
        return action;
    }

    /**
       move defines how the pet would want to move in an arena;
       note that the range of moving should be related to AGI
     */
    protected POOCoordinate move(POOArena arena){
        POOBattleField _arena = (POOBattleField)arena;
        switch(_arena.getDirection2()){
            case 'u':
                initpos.y -= getAGI();
                break;
            case 'd':
                initpos.y += getAGI();
                break;
            case 'r':
                initpos.x += getAGI();
                break;
            case 'l':
                initpos.x -= getAGI();
                break;                              
        }
        return new POOPetPos(initpos);
    }
}
