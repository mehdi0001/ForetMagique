package entity;

import utils.Constants;

import java.util.stream.Stream;

public class Hero {

    private int posX;
    private int posY;
    private Case CaseActuelle;
    private int knowledge[][];
    private Boolean visite[][];
    private static Hero hero;
    private int vie;
    private int shootUsed;
    private int point;

    public static Hero getInstance(){
        if(hero==null){
            hero = new Hero();
        }
        return hero;
    }

    private Hero() {
        vie = 0;
        shootUsed = 0;
        point =0;
        this.posY = 0;
        this.posX = 0;
        resetKnowledge(1);

    }

    public void resetKnowledge(int niveau){
        knowledge = new int[niveau+2][niveau+2];
        visite = new Boolean[niveau+2][niveau+2];
        for(int i = 0;i<niveau+2;i++) {
            for (int y = 0; y < niveau + 2; y++) {
                knowledge[i][y] = Constants.UNKNOW;
                visite[i][y] = new Boolean(false);
            }
        }
    }

    private Hero(int posY, int posX) {
        this.posY = posY;
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public Case getCaseActuelle() {
        return CaseActuelle;
    }

    public void setCaseActuelle(Case CaseActuelle) {
        this.CaseActuelle = CaseActuelle;
    }

    public int[][] getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(int[][] knowledge) {
        this.knowledge = knowledge;
    }

    public Boolean[][] getvisite() {
        return visite;
    }

    public void setvisite(Boolean[][] visite) {
        this.visite = visite;
    }

    public int getvie() {
        return vie;
    }

    public void setvie(int vie) {
        this.vie = vie;
    }

    public int getShootUsed() {
        return shootUsed;
    }

    public void setShootUsed(int shootUsed) {
        this.shootUsed = shootUsed;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void move(String dir){
        switch (dir){
            case "right":
                posY++;
                break;
            case "left":
                posY--;
                break;
            case "up":
                posX--;
                break;
            case "down":
                posX++;
                break;
        }
    }
}
