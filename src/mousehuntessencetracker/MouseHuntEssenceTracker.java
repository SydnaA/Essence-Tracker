/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mousehuntessencetracker;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 *
 * @author SydnaAgnehs
 */
public class MouseHuntEssenceTracker implements ActionListener {
    
    private int[] essence;
    private Map<Integer, JTextField> essDis;
    private Map<Integer, JTextField> essDisCal;
    private EssenceSave es;
    private String[] essenceName={"Aleth","Ber", "Cynd", "Dol", "Est","Fer", "Gur","Hix", "Icuri"};
    private JComboBox jcb;
    private JLabel author=new JLabel("By: Sydna Agnehs");
    public MouseHuntEssenceTracker() {
        this.initLook();
    }
    private void initLook() {
        JFrame frame=new JFrame("MouseHunt Essence Tracker");
        JPanel panel=new JPanel();
        
        JPanel interEssPan=new JPanel(new GridLayout(2, 3));
        JLabel enterTitle=new JLabel("Enter your data here!");
        enterTitle.setPreferredSize(new Dimension(200, 50));
        enterTitle.setVisible(true);
        panel.add(enterTitle);
        interEssPan.setPreferredSize(new Dimension(450, 100));
        this.initEssence(interEssPan);
        interEssPan.setVisible(true);
        panel.add(interEssPan);
        
        
        /*
        JPanel seconaar
         */
        
        
        JPanel interLowEssPan=new JPanel(new GridLayout(2, 3));
        JLabel calulatedLabel=new JLabel("Calculated Results");
        calulatedLabel.setPreferredSize(new Dimension(200, 50));
        calulatedLabel.setVisible(true);
        panel.add(calulatedLabel);
        interLowEssPan.setPreferredSize(new Dimension(500, 100));
        this.initCalEssence(interLowEssPan);
        interLowEssPan.setVisible(true);
        panel.add(interLowEssPan);
        
        JPanel buttonPan=new JPanel();
        buttonPan.setPreferredSize(new Dimension(500, 40));
        buttonPan.setVisible(true);
        
        JButton save=new JButton("Save");
        save.setPreferredSize(new Dimension(80, 25));
        save.setActionCommand("save");
        save.addActionListener(this);
        save.setVisible(true);
        buttonPan.add(save);
        
        JButton revert=new JButton("Load");
        revert.setPreferredSize(new Dimension(80, 25));
        revert.setActionCommand("revert");
        revert.addActionListener(this);
        //revert.setVisible(true);
        //buttonPan.add(revert);
        
        panel.add(buttonPan);
        author.setVisible(true);
        panel.add(author);
        
        
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 430);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        panel.setVisible(true);
        
    }
    private void initEssence(JPanel essPan) {
        essence=new int[9];
        essDis=new HashMap<Integer, JTextField>();
        es=new EssenceSave();
        essence=es.getEssArr();
        for(int x=0;x<9;x++) {
            essDis.put(x, new JTextField(essence[x]));
            essDis.get(x).setPreferredSize(new Dimension(60, 40));
            essDis.get(x).setActionCommand("edit");
            essDis.get(x).addActionListener(this);
            essDis.get(x).setVisible(true);
            essPan.add(essDis.get(x));
        }
        this.loadEssence();
        
    }
    private JComboBox initJCB() {
        jcb=new JComboBox(essenceName);
        jcb.setSelectedItem("Aleth");
        jcb.setActionCommand("jcb");
        jcb.addActionListener(this);
        jcb.setVisible(true);
        return jcb;
    }
    private void initCalEssence(JPanel essPan) {
        essDisCal=new HashMap<Integer, JTextField>();
         for(int x=0;x<9;x++) {
            essDisCal.put(x, new JTextField("0"));
            essDisCal.get(x).setPreferredSize(new Dimension(60, 40));
            essDisCal.get(x).setEditable(false);
            essDisCal.get(x).setVisible(true);
            essPan.add(essDisCal.get(x));
        }
         essPan.add(this.initJCB());
         this.reCal();
    }
    private int[] calEss() {
        int[] ess=new int[9];
        for(int x=0;x<ess.length;x++) {
            ess[x]=essence[x];
        }
        int temp;
        for(int x=0;x<jcb.getSelectedIndex();x++) {
            //System.out.println("Essence "+x+": "+ess[x]);
            temp=(int)(ess[x]/3);
            //System.out.println("Temp: "+temp);
            ess[x]=ess[x]-(temp*3);
            //System.out.println("ess["+x+"] :"+ess[x]);
            ess[x+1]+=temp;
            //System.out.println("ess["+(x+1)+"] :"+ess[x+1]+"\n\n");
        }
        return ess;
    }
    private void loadEssence() {
        essence=es.loadFile();
        for(int x=0;x<essence.length;x++) {
            essDis.get(x).setText(""+essence[x]);
        }
    }
    private void reCal() {
        int[] calEss=this.calEss();
        for(int x=0;x<essDisCal.size();x++) {
            essDisCal.get(x).setText(""+calEss[x]);
        }
    }
    private void save() {
        int[] temp=new int[9];
        for(int x=0;x<essDis.size();x++) {
            temp[x]=Integer.parseInt(essDis.get(x).getText());
        }
        es.savFile(temp);
        this.loadEssence();
        this.reCal();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("edit")){
            this.reCal();
        } else if(e.getActionCommand().equals("save")) {
            this.save();
            this.reCal();
        } else if(e.getActionCommand().equals("revert")) {
            this.loadEssence();
            this.reCal();
        } else if(e.getActionCommand().equals("jcb")) {
           this.reCal();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new MouseHuntEssenceTracker();
    }
}
