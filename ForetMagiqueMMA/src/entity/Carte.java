package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Carte {

    private Case[][] cases;
    private int niveau;

    public Carte(){
        niveau = 1;
    }

    public void generateniveau(){
        cases = new Case[niveau+2][niveau+2];
        //init Case
        for(int i = 0;i<niveau+2;i++) {
            for (int y = 0; y < niveau + 2; y++) {
                cases[i][y] = new Case();
            }
        }
        //setEdge
        for(int i = 0;i<niveau+2;i++) {
            for (int y = 0; y < niveau + 2; y++) {
                List<Edge> listEdge = new ArrayList<>();
                //haut
                if(0<i){
                    Edge edge = new Edge(cases[i][y],cases[i-1][y],"haut");
                    listEdge.add(edge);
                }
                //bas
                if(niveau+2>i+1){
                    Edge edge = new Edge(cases[i][y],cases[i+1][y],"bas");
                    listEdge.add(edge);
                }
                //gauche
                if(0<y){
                    Edge edge = new Edge(cases[i][y],cases[i][y-1],"gauche");
                    listEdge.add(edge);
                }
                //droite
                if(niveau+2>y+1){
                    Edge edge = new Edge(cases[i][y],cases[i][y+1],"droite");
                    listEdge.add(edge);
                }
                cases[i][y].setCases(listEdge);
            }
        }
        //set Objet;
        for(int i = 0;i<niveau+2;i++){
            for(int y = 0;y<niveau+2;y++){
                int randomNum = 0 + (int)(Math.random() * 100);
                switch (randomNum%10){
                    //hole
                    case 1:
                        addHole(i,y);
                        break;
                    //monster
                    case 2:
                        addMonster(i,y);
                        break;
                    //portal
                    case 3:
                        addPortal(i,y);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void addHole(int i, int y) {

        cases[i][y].setHole(true);
        if(0<i){
            cases[i-1][y].setWind(true);
        }
        if(niveau+2>i+1){
            cases[i+1][y].setWind(true);
        }
        if(0<y){
            cases[i][y-1].setWind(true);
        }
        if(niveau+2>y+1){
            cases[i][y+1].setWind(true);
        }
    }

    private void addPortal(int i, int y) {

        cases[i][y].setPortal(true);
    }

    private void addMonster(int i, int y) {
        cases[i][y].setMonster(true);
        if(0<i){
            cases[i-1][y].setShit(true);
        }
        if(niveau+2>i+1){
            cases[i+1][y].setShit(true);
        }
        if(0<y){
            cases[i][y-1].setShit(true);
        }
        if(niveau+2>y+1){
            cases[i][y+1].setShit(true);
        }
    }

    public Case[][] getCases() {
        return cases;
    }

    public void setCases(Case[][] cases) {
        this.cases = cases;
    }

    public int getLevel() {
        return niveau;
    }

    public void setLevel(int niveau) {
        this.niveau = niveau;
    }
}
