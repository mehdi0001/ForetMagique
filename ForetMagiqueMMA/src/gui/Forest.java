package gui;


import entity.Case;
import entity.Hero;
import ia.Agent;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Forest extends JPanel implements ActionListener {

    private int niveau;
    Agent agent;
    int nbCase;
    int counter = 0;
    JPanel panels[][];

    public Forest(){
        agent = Agent.getInstance();
        niveau = agent.getLevel();
        nbCase = niveau+2;
        setBackground(Color.WHITE);
        setLayout(new GridLayout(nbCase,nbCase));
        panels = new JPanel[nbCase][nbCase];
        for(int i=0;i<nbCase;i++){
            for(int y=0;y<nbCase;y++) {
                panels[i][y] = new JPanel();
                panels[i][y].setBackground(Color.BLUE);
                this.add(panels[i][y]);
            }
        }

        for(int i=0;i<nbCase;i++){
            for(int y=0;y<nbCase;y++) {
                JPanel panel = panels[i][y];
                //panel.removeAll();
                panel.setBackground(Color.LIGHT_GRAY);
                panel.setSize(Constants.WIDTH/(nbCase),Constants.HEIGHT/(nbCase));
                panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                //hero
                if(agent.getHero().getPosY()==i && agent.getHero().getPosX()==y){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/Hero.png");
                    img = img.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    //label.setIcon(icon);
                    label.setText("hero : " + counter++);
                    panel.add(label);
                }
                //monster
                if(agent.getMonsterVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/monster.jpg");
                    img = img.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panel.add(label);
                }
                //shit
                if(agent.getSmellVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/shit.jpeg");
                    img = img.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panel.add(label);
                }
                //hole
                if(agent.getTrouVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/hole.jpg");
                    img = img.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panel.add(label);
                }
                //wind
                if(agent.getVentVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/vent.jpg");
                    img = img.getScaledInstance(45, 45,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panel.add(label);
                }
                //portal
                if(agent.getOutVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/portal.jpeg");
                    img = img.getScaledInstance(60, 36,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panel.add(label);
                }
            }
        }




        //frequence de refresh
        Timer timer=new Timer(1000/12, this);
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println("paint");


        for(int i=0;i<nbCase;i++){
            for(int y=0;y<nbCase;y++) {
                //JPanel panel = panels[i][y];
                //panel.removeAll();
                //panel.setBackground(Color.ORANGE);
                //panel.setSize(Constants.WIDTH/(nbCase),Constants.HEIGHT/(nbCase));
                //panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                panels[i][y].removeAll();
                panels[i][y].updateUI();
                //hero
                if(agent.getHero().getPosX()==i && agent.getHero().getPosY()==y){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/Hero.png");
                    img = img.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    //label.setText("hero : " + counter++);
                    panels[i][y].add(label);
                }
                //monster
                if(agent.getMonsterVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/monster.jpg");
                    img = img.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panels[i][y].add(label);
                }
                //shit
                if(agent.getSmellVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/shit.jpeg");
                    img = img.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panels[i][y].add(label);
                }
                //hole
                if(agent.getTrouVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/hole.jpg");
                    img = img.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panels[i][y].add(label);
                }
                //wind
                if(agent.getVentVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/vent.jpg");
                    img = img.getScaledInstance(45, 45,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panels[i][y].add(label);
                }
                //portal
                if(agent.getOutVariable()[i][y]){
                    JLabel label = new JLabel();
                    Image img = Toolkit.getDefaultToolkit().getImage("./ressources/portal.jpeg");
                    img = img.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(img);
                    label.setIcon(icon);
                    panels[i][y].add(label);
                }

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
