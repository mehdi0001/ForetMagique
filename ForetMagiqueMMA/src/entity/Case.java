package entity;

import ia.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Case {

    private boolean vent;
    private boolean trou;
    private boolean puanteur;
    private boolean monster;
    private boolean portail;

    private List<Edge> cases;
    private List<Variable> variables;

    public Case() {

        Variable trou = new Variable("trou");
        Variable monster = new Variable("Monster");
        variables = new ArrayList<>();
        variables.add(trou);
        variables.add(monster);
    }

    public boolean estVent() {
        return vent;
    }

    public void setVent(boolean vent) {
        this.vent = vent;
    }

    public boolean estTrou() {
        return trou;
    }

    public void setTrou(boolean trou) {
        this.trou = trou;
    }

    public boolean isPuanteur() {
        return puanteur;
    }

    public void setPuanteur(boolean puanteur) {
        this.puanteur = puanteur;
    }

    public boolean isMonster() {
        return monster;
    }

    public void setMonster(boolean monster) {
        this.monster = monster;
    }

    public boolean estPortail() {
        return portail;
    }

    public void setPortail(boolean portail) {
        this.portail = portail;
    }

    public List<Edge> getCases() {
        return cases;
    }

    public void setCases(List<Edge> cases) {
        this.cases = cases;
    }
}
