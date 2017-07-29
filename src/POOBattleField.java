package ntu.csie.oop13spring;
//import ntu.csie.oop13spring.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class POOBattleField extends POOArena{

    private ArrayList<POOPetPos> allpos = new ArrayList<POOPetPos>(0);      //when we add pet1 and pet2 for the first time and we need to call getallpos()
    private POOPetPos[] allpetpos = new POOPetPos[0];                       //we access any pets' position directly from this array
    private POOPet[] all = new POOPet[0];                                   //we access any pets directly from this array in order to let pets move or act
    private char[] direction = {'d', 'u'};                                  //we could know the direction of pets they face
    private int TURN = 0;                                                   //pets take turn to move or act
    private POOPetCat p1;
    private POOPetDog p2;
    private boolean start = false;                                          //game starts?
    private int slowdown = 0;                                               //pets' AGI might be larger than 1, so they sometimes need to move slowly

    /*      GUI       */
    JPanel text_buttonPn1;                          //on the left side
    JPanel text_buttonPn2;                          //on the right side
    JPanel[] proPn = new JPanel[2];
    protected JLabel[] hpNumlab = new JLabel[2];
    protected JLabel[] mpNumlab = new JLabel[2];
    protected JLabel[] agiNumlab = new JLabel[2];
    protected JLabel[] hpTextlab = new JLabel[2];
    protected JLabel[] mpTextlab = new JLabel[2];
    protected JLabel[] agiTextlab = new JLabel[2];

    MovePn movePn1;                             //on the left side
    MovePn movePn2;                             //on the right side

    JLabel namelab1;                            //on the left side
    JLabel namelab2;                            //on the right side

    JButton actionButton1;
    JButton actionButton2;
    JLabel[] turn = new JLabel[2];              //let players know what they should do now

    String fname [] = {"image/hello-kitty.png", "image/snoopy.png", "image/background.png"};
    ImageIcon imc [] = {new ImageIcon(fname[0]), new ImageIcon(fname[1]), new ImageIcon(fname[2])};

    String[] textname = {"image/turn.png", "image/waiting.png", "image/fight.png"};
    ImageIcon[] situation = {new ImageIcon(textname[0]), new ImageIcon(textname[1]), new ImageIcon(textname[2])};

    String[] gameOver = {"image/G.png", "image/A.png", "image/M.png", "image/E.png", "image/O.png", "image/V.png", "image/R.png",
                         "image/black.png", "image/win.png", "image/lose.png"};
    ImageIcon[] bye = {new ImageIcon(gameOver[0]), new ImageIcon(gameOver[1]), new ImageIcon(gameOver[2]), new ImageIcon(gameOver[3]),
                        new ImageIcon(gameOver[4]), new ImageIcon(gameOver[5]), new ImageIcon(gameOver[3]), new ImageIcon(gameOver[6]),
                        new ImageIcon(gameOver[7]), new ImageIcon(gameOver[8]), new ImageIcon(gameOver[9])};

    JLabel[] imgLabel = new JLabel[25];         //all will be added to mapPn

    public POOBattleField(){
    }
    public boolean fight(){
        if(start == false){                     //when POOFight first call this function, we initialize our POOBattleField
            start = true;
            init();
        }
        if(p1.getHP() <= 0 || p2.getHP() <= 0){ //load the "GAME OVER" picture
            for(int i = 0; i < 25; i++){
                if(i == 5)  imgLabel[i].setIcon(bye[0]);
                else if(i == 6) imgLabel[i].setIcon(bye[1]);
                else if(i == 7) imgLabel[i].setIcon(bye[2]);
                else if(i == 8) imgLabel[i].setIcon(bye[3]);
                else if(i == 11)imgLabel[i].setIcon(bye[4]);
                else if(i == 12)imgLabel[i].setIcon(bye[5]);
                else if(i == 13)imgLabel[i].setIcon(bye[6]);
                else if(i == 14)imgLabel[i].setIcon(bye[7]);
                else imgLabel[i].setIcon(bye[8]);
            }
            if(p1.getHP() == 0){                //show who is the winner and who is the loser
                turn[0].setIcon(bye[10]);
                turn[1].setIcon(bye[9]);
            }
            else{
                turn[0].setIcon(bye[9]);
                turn[1].setIcon(bye[10]);
            }
            /*disable all the buttons*/
            actionButton1.setEnabled(false);
            actionButton2.setEnabled(false);
            movePn1.upButton.setEnabled(false);
            movePn1.downButton.setEnabled(false);
            movePn1.rightButton.setEnabled(false);
            movePn1.leftButton.setEnabled(false);
            movePn2.upButton.setEnabled(false);
            movePn2.downButton.setEnabled(false);
            movePn2.rightButton.setEnabled(false);
            movePn2.leftButton.setEnabled(false);
            return false;
        }
        else return true;
    }
    public void show(){
    }
    
    public void init(){
        JFrame pooframe = new JFrame("POOBattleField");
        pooframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        all = getAllPets();
        for(int i = 0; i < all.length; i++){
            if(all[i] instanceof POOPetCat)
                p1 = (POOPetCat)all[i];
            else if(all[i] instanceof POOPetDog)
                p2 = (POOPetDog)all[i];
        }
        allpos.add(p1.getInitPos());
        allpos.add(p2.getInitPos());
        allpetpos = getallpos();

    	actionButton1 = new JButton(p1.getSkillName());
        actionButton2 = new JButton(p2.getSkillName());

        turn[0] = new JLabel(situation[0]);             //player1 goes first
        turn[1] = new JLabel(situation[1]);


        namelab1 = new JLabel();        setPlayerLabel(namelab1, "Player1 "+p1.getName());
        namelab2 = new JLabel();        setPlayerLabel(namelab2, "Player2 "+p2.getName());

    	Container cp = pooframe.getContentPane();
    	JPanel mapPn = new MapPn();
    	text_buttonPn1 = new JPanel(new GridLayout(5,1));
        text_buttonPn2 = new JPanel(new GridLayout(5,1));
        
        movePn1 = new MovePn("0");
        movePn2 = new MovePn("1");

        proPn[0] = new JPanel();    proPn[0].setLayout(new GridLayout(2,3));
        proPn[1] = new JPanel();    proPn[1].setLayout(new GridLayout(2,3));

        hpTextlab[0] = new JLabel();    setProLabel(hpTextlab[0], "HP");    
        hpTextlab[1] = new JLabel();    setProLabel(hpTextlab[1], "HP");
        mpTextlab[0] = new JLabel();    setProLabel(mpTextlab[0], "MP");
        mpTextlab[1] = new JLabel();    setProLabel(mpTextlab[1], "MP");
        agiTextlab[0] = new JLabel();   setProLabel(agiTextlab[0], "AGI");
        agiTextlab[1] = new JLabel();   setProLabel(agiTextlab[1], "AGI");       
        
        hpNumlab[0] = new JLabel();     setProLabel(hpNumlab[0], ""+p1.getHP());   
        hpNumlab[1] = new JLabel();     setProLabel(hpNumlab[1], ""+p2.getHP());
        mpNumlab[0] = new JLabel();     setProLabel(mpNumlab[0], ""+p1.getMP());
        mpNumlab[1] = new JLabel();     setProLabel(mpNumlab[1], ""+p2.getMP());
        agiNumlab[0] = new JLabel();    setProLabel(agiNumlab[0], ""+p1.getAGI()); 
        agiNumlab[1] = new JLabel();    setProLabel(agiNumlab[1], ""+p2.getAGI());

        proPn[0].add(hpTextlab[0]);     proPn[0].add(mpTextlab[0]);     proPn[0].add(agiTextlab[0]);
        proPn[0].add(hpNumlab[0]);      proPn[0].add(mpNumlab[0]);      proPn[0].add(agiNumlab[0]);
        proPn[1].add(hpTextlab[1]);     proPn[1].add(mpTextlab[1]);     proPn[1].add(agiTextlab[1]);
        proPn[1].add(hpNumlab[1]);      proPn[1].add(mpNumlab[1]);      proPn[1].add(agiNumlab[1]);

        /*
        we add all those components to text_buttonPn by the following order
           1.  turn
    	   2.  namelab
    	   3.  JPanel proPn = new JPanel(new GridLayout(2,3));
    	   4.  actionButton
    	   5.  JPanel movePn = new JPanel(new GridLayout(2,3));
        */
        text_buttonPn1.add(turn[0]);
    	text_buttonPn1.add(namelab1);       text_buttonPn1.add(proPn[0]);     text_buttonPn1.add(actionButton1);       text_buttonPn1.add(movePn1);
        text_buttonPn2.add(turn[1]);
        text_buttonPn2.add(namelab2);       text_buttonPn2.add(proPn[1]);     text_buttonPn2.add(actionButton2);       text_buttonPn2.add(movePn2);
        
    	actionButton1.addActionListener(getActionButtonListener());
        actionButton2.addActionListener(getActionButtonListener());

        pooframe.add(text_buttonPn1,BorderLayout.WEST);
        pooframe.add(mapPn,BorderLayout.CENTER);
        pooframe.add(text_buttonPn2,BorderLayout.EAST);
        pooframe.pack();
        
        pooframe.setResizable(false);
        pooframe.setVisible(true);
    }

    public void setPlayerLabel(JLabel lbl, String name){
        lbl.setText(name);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setForeground(new Color(70,155,255));
        lbl.setFont(new Font("Dialog", Font.BOLD, 32));
        lbl.setOpaque(true);
    }
    public void setProLabel(JLabel lbl, String name){
        lbl.setText(name);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setForeground(new Color(255,240,0));
        lbl.setBackground(new Color(0,0,255));
        lbl.setFont(new Font("Dialog", Font.BOLD, 24));
        lbl.setOpaque(true);
    }
    public char getDirection1()  {return direction[0];}
    public char getDirection2()  {return direction[1];}
    public int getSlowdown()     {return slowdown;}
    
    public final POOPetPos[] getallpos(){
        POOPetPos[] parr = new POOPetPos[0];
        return allpos.toArray(parr);
    }
    
    public POOCoordinate getPosition(POOPet p){

        if(p == all[0])
            return new POOPetPos(allpos.get(0));
        else //if(p == all[1])
            return new POOPetPos(allpos.get(1));

    }
    /*isMovable will tell you whether pet could move and how much steps the pets might walk*/
    public boolean isMovable(int me, String dirbutton){     
        int enemy = 0;
        if(TURN == 0)   enemy = 1;
        int oldX = allpetpos[me].x, oldY = allpetpos[me].y;
        int newX, newY;
        int agi = all[me].getAGI();
        if(dirbutton == "up"){
            newY = oldY - agi;
            if(allpetpos[enemy].x == oldX && oldY > allpetpos[enemy].y && newY <= allpetpos[enemy].y){  //go through
                //never mind, pet might move less steps to get closer to its enemy
                while(slowdown < agi){
                    if(newY + slowdown > allpetpos[enemy].y ){
                        direction[me] = 'u';
                        return true;
                    }
                    else slowdown++;
                }
                return false;
            }
            else if(newY < 0)   return false;
            else direction[me] = 'u';
        }
        else if(dirbutton == "down"){
            newY = oldY + agi;
            if(allpetpos[enemy].x == oldX && oldY < allpetpos[enemy].y && newY >= allpetpos[enemy].y){  //go through
                //never mind, pet might move less steps to get closer to its enemy
                while(slowdown < agi){
                    if(newY - slowdown < allpetpos[enemy].y){
                        direction[me] = 'd';
                        return true;
                    }
                    else slowdown++;
                }
                return false;
            }
            else if(newY > 4)   return false;
            else direction[me] = 'd';
        }
        else if(dirbutton == "right"){
            newX = oldX + agi;
            if(allpetpos[enemy].y == oldY && oldX < allpetpos[enemy].x && newX >= allpetpos[enemy].x){  //go through
                //never mind, pet might move less steps to get closer to its enemy
                while(slowdown < agi){
                    if(newX - slowdown < allpetpos[enemy].x){
                        direction[me] = 'r';
                        return true;
                    }
                    else slowdown++;
                }
                return false;
            }
            else if(newX > 4)   return false;
            else direction[me] = 'r';
        }
        else if(dirbutton == "left"){
            newX = oldX - agi;
            if(allpetpos[enemy].y == oldY && oldX > allpetpos[enemy].x && newX <= allpetpos[enemy].x){  //go through
                //never mind, pet might move less steps to get closer to its enemy
                while(slowdown < agi){
                    if(newX + slowdown > allpetpos[enemy].x){
                        direction[me] = 'l';
                        return true;
                    }
                    else slowdown++;
                }
                return false;
            }
            else if(newX < 0)   return false;
            else direction[me] = 'l';
        }
        return true;
    }

    public ActionButtonListener getActionButtonListener(){
        return new ActionButtonListener();
    }
    /*solve the problem that I don't know what parameter I should add to the funciton "protected POOAction act(POOArena arena)"
     *when the followint codes are put in the inner class "ActionButtonListener"
     */
    public void solver1(POOAction my_act, int mp){
        if(TURN == 0)   my_act = p1.act(this);
        else my_act = p2.act(this);

        int mpRequire;
        if(my_act.skill instanceof POOCrazyScratchSkill)
            mpRequire = ((POOCrazyScratchSkill)my_act.skill).mpRequire;
        else //if(my_act.skill instanceof POOCrazyBiteSkill)
            mpRequire = ((POOCrazyBiteSkill)my_act.skill).mpRequire;
        if(TURN == 0){
            mp = p1.getMP();
            if(mp < mpRequire){         //sorry, pet's MP points are not sufficient, so pet should rest for one turn
                p1.setMP(mp+1);
                mpNumlab[TURN].setText(""+p1.getMP());
                return;
            }
            if(my_act.skill instanceof POOCrazyScratchSkill){
                ((POOCrazyScratchSkill)my_act.skill).act(my_act.dest, p1);
            }
        }
        else{
            mp = p2.getMP();
            if(mp < mpRequire){         //sorry, pet's MP points are not sufficient, so pet should rest for one turn
                p2.setMP(mp+1);
                mpNumlab[TURN].setText(""+p2.getMP());
                return;
            }
            if(my_act.skill instanceof POOCrazyBiteSkill){
                ((POOCrazyBiteSkill)my_act.skill).act(my_act.dest, p2);
            }
        }
        /* update the newest infos of pets */
        mpNumlab[0].setText(""+p1.getMP());
        mpNumlab[1].setText(""+p2.getMP());
        hpNumlab[0].setText(""+p1.getHP());
        hpNumlab[1].setText(""+p2.getHP());
    }
    /* isAround will tell you whether any other pets are around you */
    public boolean isAround(int me){
        int enemy = 0;
        if(me == 0) enemy = 1;
        int myX = allpetpos[me].x, myY = allpetpos[me].y, enemyX = allpetpos[enemy].x, enemyY = allpetpos[enemy].y;
        if(myX == enemyX && (myY == enemyY+1 || myY == enemyY-1))   return true;
        else if(myY == enemyY && (myX == enemyX+1 || myX == enemyX-1))   return true;
        else return false;
    }
    /* one pet could fight with another pet */
    private class ActionButtonListener implements ActionListener{
        POOPetPos mypos, enemypos;
        POOAction my_act;
        int mp;
        public void actionPerformed(ActionEvent e){
            if(TURN == 0 && e.getSource()==actionButton2)   return;
            else if(TURN == 1 && e.getSource()==actionButton1)  return;
            if(isAround(TURN) == false) return;

            solver1(my_act, mp);
            turn[TURN].setIcon(situation[1]);
            if(TURN == 0)   TURN = 1;
            else TURN = 0;
            turn[TURN].setIcon(situation[2]);
        }
    }
    public MoveListener getMoveListener(){
        return new MoveListener();
    }
    /*solve the problem that I don't know what parameter I should add to the funciton "protected POOCoordinate move(POOArena arena)"
     *when the followint codes are put in the inner class "MoveListener"
     */
    public void solver2(POOPet pet){
        if(pet instanceof POOPetCat)
            allpetpos[TURN] = (POOPetPos)p1.move(this);
        else if(pet instanceof POOPetDog)
            allpetpos[TURN] = (POOPetPos)p2.move(this);
        slowdown = 0;
    }
    private class MoveListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //if(moveFlag[TURN] == false) return;
            String type = e.getActionCommand();
            int who = Integer.parseInt(type);
            if(who == 0 && TURN == 1)       return;
            else if(who == 1 && TURN == 0)  return;
            
            JButton dirbutton = (JButton)e.getSource();

            if(isMovable(who, dirbutton.getText())){
                imgLabel[allpetpos[TURN].x+allpetpos[TURN].y*5].setIcon(imc[2]);    //reset image of the specific area
                if(TURN == 0)
                    solver2(p1);
                else
                    solver2(p2);
                imgLabel[allpetpos[TURN].x+allpetpos[TURN].y*5].setIcon(imc[TURN]); //pet is here now
                if(isAround(TURN) == false){                                        //pet could not fight, so turn is shifted
                    turn[TURN].setIcon(situation[1]);
                    if(TURN == 0)   TURN = 1;
                    else TURN = 0;
                    turn[TURN].setIcon(situation[0]);
                }
                else{                                                               //the pet could fight with another pet
                    turn[TURN].setIcon(situation[2]);
                }
            }
        }
    }
    public class MovePn extends JPanel{
        protected JButton upButton;
        protected JButton downButton;
        protected JButton rightButton;
        protected JButton leftButton;
        public MovePn(String actIndex){
            upButton = newJButton("up", actIndex);
            downButton = newJButton("down", actIndex);            
            rightButton = newJButton("right", actIndex);
            leftButton = newJButton("left", actIndex);
            setLayout(new GridLayout(2,3));
            add(new JLabel());          add(upButton);      add(new JLabel());
            add(leftButton);    add(downButton);    add(rightButton);

        }
        private JButton newJButton(String str, String actIndex) {
            JButton btn = new JButton(str);
            btn.setActionCommand(actIndex);
            btn.addActionListener(getMoveListener());
            return btn;
        }
    }
    public class MapPn extends JPanel{
        public MapPn(){
            setLayout(new GridLayout(5,5));
            for(int i = 0; i < 25; i++){
                if(i == 0)  imgLabel[i] = new JLabel(imc[0]);
                else if(i == 24)  imgLabel[i] = new JLabel(imc[1]);
                else imgLabel[i] = new JLabel(imc[2]);
                add(imgLabel[i]);
            }
        }
        
    }
}


