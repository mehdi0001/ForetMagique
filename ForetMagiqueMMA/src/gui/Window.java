package gui;

import entity.Hero;
import ia.Agent;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Window extends JFrame {

    private static Window window;
    private JPanel forest;
    private JLabel storyField;

    public static Window getInstance(){
        if(window == null){
            window = new Window();
        }
        return window;
    }

    private Window(){
        setTitle("Foret Magique");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container container = getContentPane();
        setSize(Constants.WIDTH,Constants.HEIGHT);
        forest = new Forest();
        container.add(forest);


        JPanel storyBoard = new JPanel();
        storyBoard.setBackground(Color.red);
        storyBoard.setSize(Constants.WIDTH,100);
        //action button
        JButton bouger = new JButton("bouger");
        bouger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bouger");
                Agent.getInstance().findMove();
            }
        });
        storyBoard.add(bouger,BorderLayout.WEST);
        JButton newLevel = new JButton("generation niveau");
        newLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("generation niveau");
                Agent.getInstance().newLevel();
            }
        });
        storyBoard.add(newLevel,BorderLayout.WEST);
        //Story label
        storyField = new JLabel("Foret magique performance : "
                + Hero.getInstance().getLife() + " vie , "
                + Hero.getInstance().getShootUsed() + " tir , gain "
                + Hero.getInstance().getPoint() + " point");
        storyBoard.add(storyField);

        container.add(storyBoard,BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        storyField.setText("Foret magique performance : "
                + Hero.getInstance().getLife() + " vie , "
                + Hero.getInstance().getShootUsed() + " tir , gain "
                + Hero.getInstance().getPoint() + " point");

    }

    public void newForest() {
        Container c = this.getContentPane() ;
        c.remove(0);
        c.repaint();
        forest = new Forest();
        c.add(forest,0);
        this.repaint();
        this.revalidate();
    }

}
