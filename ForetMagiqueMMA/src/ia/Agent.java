package ia;

import entity.Case;
import entity.Hero;
import gui.Window;
import utils.Constants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static java.lang.Thread.sleep;

public class Agent{

    private Hero hero;
    private static Agent agent;
    private List<Rules> rules;
    private int niveau;
    private Boolean VentVariable[][];
    private Boolean smellVariable[][];
    private Boolean TrouVariable[][];
    private Boolean monsterVariable[][];
    private Boolean outVariable[][];
    private String dir;
    private HashMap<String,Boolean> mapDir;
    private int lMonster;
    private int cMonster;
    private int lPortail;
    private int cPortail;
    private int lTrou;
    private int cTrou;

    private Agent() {
        instanciateMapDir();
        niveau = 1;
        hero = Hero.getInstance();
        dir = "right";
        rules = new ArrayList<>();

        //if out actions levelUp
        Rules r = new Rules();
        r.setLabel(1);
        r.get_true().add("OutVariable");
        r.getActions().add("levelUp");
        rules.add(r);
        //if out actions levelUp
        r = new Rules();
        r.setLabel(2);
        r.get_true().add("MonsterVariable");
        r.getActions().add("death");
        rules.add(r);
        //if out actions levelUp
        r = new Rules();
        r.setLabel(3);
        r.get_true().add("TrouVariable");
        r.getActions().add("death");
        rules.add(r);
        //if visited and dir available change dir
        r = new Rules();
        r.setLabel(4);
        r.get_trueDir().add("Visited");
        r.setDirAvailable(true);
        r.getActions().add("changeDir");
        rules.add(r);
        //if pas odeur pas vent et jamais visiter move;
        r = new Rules();
        r.setLabel(5);
        r.get_false().add("SmellVariable");
        r.get_false().add("VentVariable");
        r.get_falseDir().add("Visited");
        r.getActions().add("move");
        rules.add(r);
        //if pas odeur pas vent et visiter move;
        r = new Rules();
        r.setLabel(6);
        r.get_false().add("SmellVariable");
        r.get_false().add("VentVariable");
        r.get_trueDir().add("Visited");
        r.getActions().add("move");
        rules.add(r);
        //if odeur pas vent et jamais visiter shoot and move;
        r = new Rules();
        r.setLabel(7);
        r.get_true().add("SmellVariable");
        r.get_false().add("VentVariable");
        r.get_falseDir().add("Visited");
        r.getActions().add("shoot");
        r.getActions().add("move");
        rules.add(r);
        //if vent et pas visiter move
        r = new Rules();
        r.setLabel(8);
        r.get_true().add("VentVariable");
        r.get_falseDir().add("Visited");
        r.getActions().add("move");
        //if vent et visiter move
        rules.add(r);
        r = new Rules();
        r.setLabel(9);
        r.get_true().add("VentVariable");
        r.getActions().add("move");
        rules.add(r);

        newLevel();
    }

    public static Agent getInstance(){
        if(agent == null){
            agent = new Agent();
        }
        return agent;
    }

    public void findMove(){
        int local = niveau;
        if (!dirExist(dir)) {
            changeDir();
        }
        addKnow();//add connaissance de la case actuelle

        //check rules ajouter une a une et dans ordre d'interet execute l'action de la premiere regle qui colle
        for (Rules r : rules
                ) {
            if (r.isOK(hero.getPosX(), hero.getPosY(), dir)) {
                for (String s : r.getActions()
                        ) {
                    try {
                        Method m = this.getClass().getMethod(s);
                        m.invoke(this);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    public void move(){
        //reset diravailable
        instanciateMapDir();
        hero.move(dir);
        hero.setPoint(hero.getPoint()-1);
    }

    public void levelUp(){
        hero.setPoint(hero.getPoint()+(10*(niveau+2)));
        niveau ++;
        hero.setPosX(0);
        hero.setPosY(0);
        hero.resetKnowledge(niveau);
        newLevel();
        Window.getInstance().newForest();
        Window.getInstance().repaint();
    }

    public void death(){
        System.out.println("death");
        hero.setPoint(hero.getPoint()-(10*(niveau+2)));
        hero.setvie(hero.getvie()+1);
        hero.setPosX(0);
        hero.setPosY(0);
    }

    public void shoot(){
        switch (dir){
            case "right":
                monsterVariable[hero.getPosX()][hero.getPosY()+1] = false;
                break;
            case "left":

                monsterVariable[hero.getPosX()][hero.getPosY()+1] = false;
                break;
            case "up":
                monsterVariable[hero.getPosX()-1][hero.getPosY()] = false;
                break;
            case "down":
                monsterVariable[hero.getPosX()+1][hero.getPosY()] = false;
                break;
        }
        hero.setPoint(hero.getPoint()-10);
        hero.setShootUsed(hero.getShootUsed()+1);
    }

    public void addKnow(){
        hero.getvisite()[hero.getPosX()][hero.getPosY()] = true;

        if(TrouVariable[hero.getPosX()][hero.getPosY()]){
            hero.getKnowledge()[hero.getPosX()][hero.getPosY()] = Constants.HOLE;
        }
        if(monsterVariable[hero.getPosX()][hero.getPosY()]){
            hero.getKnowledge()[hero.getPosX()][hero.getPosY()] = Constants.MONSTER;
        }
    }

    public void newLevel() {
        int nbCase = niveau + 2;
        VentVariable = new Boolean[nbCase][nbCase];
        smellVariable = new Boolean[nbCase][nbCase];
        TrouVariable = new Boolean[nbCase][nbCase];
        monsterVariable = new Boolean[nbCase][nbCase];
        outVariable = new Boolean[nbCase][nbCase];

        for (int i = 0; i < nbCase; i++) {
            for (int y = 0; y < nbCase; y++) {
                VentVariable[i][y] = new Boolean(false);
                smellVariable[i][y] = new Boolean(false);
                TrouVariable[i][y] = new Boolean(false);
                monsterVariable[i][y] = new Boolean(false);
                outVariable[i][y] = new Boolean(false);
            }
        }

        //Ajouter portail
        lPortail =  new Random().nextInt(nbCase);
        cPortail = new Random().nextInt(nbCase);
        while(lPortail==hero.getPosX() && cPortail==hero.getPosY()) {
	        lPortail =  new Random().nextInt(nbCase);
	        cPortail = new Random().nextInt(nbCase);
        }
        addPortail(lPortail,cPortail);
        //Creation des monstres:  x monstre pour x niveau
        
            //les regles de generation sont presentes dans isMonsterCanStayHere pour les monstres
        	//les regles de generation sont presentes dans isTrouCanStayHere pour les trous
            // les monstres ne doivent pas se generer ni sur le heros ni a cote
        for (int i = 0; i < niveau; ++i) {	
    		while(isMonsterCanStayHere(lMonster,cMonster,monsterVariable)==false
    				|| (lMonster-1==hero.getPosX() && cMonster==hero.getPosY())
    				|| (cMonster-1==hero.getPosY() && lMonster==hero.getPosX())
    				|| (lMonster==hero.getPosX() && cMonster==hero.getPosY())) {
	        	lMonster =  new Random().nextInt(nbCase);
	        	cMonster = new Random().nextInt(nbCase);
	        	   
    		}
    		
    		addObject(lMonster, cMonster,monsterVariable,smellVariable);
    		
            }
           
        for (int i = 0; i < niveau; ++i) {	
    		while(isTrouCanStayHere(lTrou,cTrou,monsterVariable,TrouVariable)==false
    				|| (lTrou-1==hero.getPosX() && cTrou==hero.getPosY())
    				|| (lTrou==hero.getPosX() && cTrou-1==hero.getPosY())
    				|| (lTrou==hero.getPosX() && cTrou==hero.getPosY())) {
	        	lTrou =  new Random().nextInt(nbCase);
	        	cTrou = new Random().nextInt(nbCase);
	        	   
    		}
    		
    		addObject(lTrou, cTrou,TrouVariable,VentVariable);
    		
            }

    }
    

        public boolean isMonsterCanStayHere(int i, int y, Boolean[][] object){
            //Interdit d'etre sur le portaile ou à coté (en haut/bas/gauche/droite)
        	int nbCase = niveau + 2; 
        	if (
                     getlPortail() == getlMonster() && getcPortail() == getcMonster()
                    || getlPortail()+1 == getlMonster() && getcPortail() == getcMonster()
                     || getlPortail()-1 == getlMonster() && getcPortail() == getcMonster()
                     || getlPortail() == getlMonster() && getcPortail()+1 == getcMonster()
                     || getlPortail() == getlMonster() && getcPortail()-1 == getcMonster()
                     || object[i][y] == true
             ){
                 return false;
             }
        	if (i!=0 && object[i-1][y] == true) {
        		return false;
        	}
        	if (y!=0 && object[i][y-1] == true) {
        		return false;
        	}
        	if (i!=nbCase-1 && object[i+1][y] == true) {
        		return false;
        	}
        	if (y!=nbCase-1 && object[i][y+1] == true) {
        		return false;
        	}
             return true;
        }
        
        public boolean isTrouCanStayHere(int i, int y, Boolean[][] object, Boolean[][] second){
            //Interdit d'etre sur le portaile ou à coté (en haut/bas/gauche/droite)
        	int nbCase = niveau + 2; 
        	if (
                     getlPortail() == getlTrou() && getcPortail() == getcTrou()
                    || getlPortail()+1 == getlTrou() && getcPortail() == getcTrou()
                     || getlPortail()-1 == getlTrou() && getcPortail() == getcTrou()
                     || getlPortail() == getlTrou() && getcPortail()+1 == getcTrou()
                     || getlPortail() == getlTrou() && getcPortail()-1 == getcTrou()
                     || second[i][y] == true
                     || object[i][y] == true
             ){
                 return false;
             }
        	if (i!=0 && object[i-1][y] == true) {
        		return false;
        	}
        	if (y!=0 && object[i][y-1] == true) {
        		return false;
        	}
        	if (i!=nbCase-1 && object[i+1][y] == true) {
        		return false;
        	}
        	if (y!=nbCase-1 && object[i][y+1] == true) {
        		return false;
        	}
        	if (i!=0 && second[i-1][y] == true) {
        		return false;
        	}
        	if (y!=0 && second[i][y-1] == true) {
        		return false;
        	}
        	if (i!=nbCase-1 && second[i+1][y] == true) {
        		return false;
        	}
        	if (y!=nbCase-1 && second[i][y+1] == true) {
        		return false;
        	}
           
            return true;
        }  
        
    private void addPortail(int i, int y) {

        outVariable[i][y] = true;
    }

    private void addObject(int i, int y,Boolean[][] object,Boolean[][] indice) {
        object[i][y] = true;
        if(0<i){
            indice[i-1][y] = true;
        }
        if(niveau+2>i+1){
            indice[i+1][y] = true;
        }
        if(0<y){
            indice[i][y-1] = true;
        }
        if(niveau+2>y+1){
            indice[i][y+1] = true;
        }
    }

    public int getLevel() {
        return niveau;
    }

    public void setLevel(int niveau) {
        this.niveau = niveau;
    }

    public Boolean[][] getMonsterVariable() {
        return monsterVariable;
    }

    public void setMonsterVariable(Boolean[][] monsterVariable) {
        this.monsterVariable = monsterVariable;
    }

    public Boolean[][] getVentVariable() {
        return VentVariable;
    }

    public void setVentVariable(Boolean[][] VentVariable) {
        this.VentVariable = VentVariable;
    }

    public Boolean[][] getSmellVariable() {
        return smellVariable;
    }

    public void setSmellVariable(Boolean[][] smellVariable) {
        this.smellVariable = smellVariable;
    }

    public Boolean[][] getTrouVariable() {
        return TrouVariable;
    }

    public void setTrouVariable(Boolean[][] TrouVariable) {
        this.TrouVariable = TrouVariable;
    }

    public Boolean[][] getOutVariable() {
        return outVariable;
    }

    public void setOutVariable(Boolean[][] outVariable) {
        this.outVariable = outVariable;
    }

    public Hero getHero() {
        return hero;
    }

    public Boolean[][] getVisited() {
        return hero.getvisite();
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public int getlMonster() {
        return lMonster;
    }

    public int getcMonster() {
        return cMonster;
    }

    public int getlPortail() {
        return lPortail;
    }

    public int getcPortail() {
        return cPortail;
    }

    public int getlTrou() {
        return lTrou;
    }

    public int getcTrou() {
        return cTrou;
    }
    

    public boolean dirAvailable(){
        for (Map.Entry<String, Boolean> entry : mapDir.entrySet())
        {
            //si une dir false reste dir donc true
            if(!entry.getValue()){
               return true;
            }
        }
        return false;
    }

    //remettre a 0 a chaque inference
    public void instanciateMapDir() {
        mapDir = new HashMap<>();
        mapDir.put("down",false);
        mapDir.put("left",false);
        mapDir.put("up",false);
        mapDir.put("right",false);
    }

    //si dir existe
    public boolean dirExist(String dir) {
        switch (dir){
            case "right":
                if(hero.getPosY()+1>niveau+1){
                    return false;
                }
                break;
            case "left":
                if(hero.getPosY()-1<0){
                    return false;
                }
                break;
            case "up":
                if(hero.getPosX()-1<0){
                    return false;
                }
                break;
            case "down":
                if(hero.getPosX()+1>niveau+1){
                    return false;
                }
                break;
        }
        return true;
    }

    public void changeDir() {
        for (Map.Entry<String, Boolean> entry : mapDir.entrySet())
        {
            if(!entry.getValue()){
                mapDir.put(entry.getKey(),true);
                if(dirExist(entry.getKey())) {
                    dir = entry.getKey();
                    break;
                }
            }
        }

    }


}
