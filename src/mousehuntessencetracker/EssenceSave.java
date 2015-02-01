/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mousehuntessencetracker;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author SydnaAgnehs
 */
public class EssenceSave {
    private String folderLoc="/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/MouseHunt Essence Tracker/";
    private String savLoc_sav="/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/MouseHunt Essence Tracker/essence.sav";
    private String savLoc_txt="/Users/"+System.getProperty("user.name")+"/Library/Application Support/ProtoStar Softwares/MouseHunt Essence Tracker/essence.txt";
    private String folderLoc_win="C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\ProtoStar Softwares\\Mousehunt Essence Tracker\\";
    private String savLoc_sav_win="C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\ProtoStar Softwares\\Mousehunt Essence Tracker\\essence.sav";
    private String savLoc_txt_win="C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\ProtoStar Softwares\\Mousehunt Essence Tracker\\essence.txt";
    private File savFile_sav;
    private File savFile_txt;
    private int[] ess;
    public EssenceSave() {
        if(this.isMac()) {
            savFile_sav=new File(savLoc_sav);
            savFile_txt=new File(savLoc_txt);
        } else {
            savFile_sav=new File(savLoc_sav_win);
            savFile_txt=new File(savLoc_txt_win);
        }
        ess=new int[9];
        if(savFile_sav.exists()&&savFile_sav.canRead()&&savFile_sav.canWrite()) {
            this.loadFile();
        } else {
            this.savFile(null);
        }
    }
    public int[] getEssArr() {
        return ess;
    }
    public void setEssArr(int[] ess) {
        this.ess=ess;
    }
    public int[] loadFile() {
        ess=new int[9];
        try {
            savFile_sav.renameTo(savFile_txt);
            Scanner scanner=new Scanner(savFile_txt);
            int x=0;
            while(scanner.hasNext())
            {
                ess[x++]=Integer.parseInt(scanner.nextLine());
            }
            scanner.close();
            savFile_txt.renameTo(savFile_sav);
            return ess;
        } catch (FileNotFoundException ex) {
            return ess;
        }
    }
    public boolean savFile(int[] ess) {
        File folder=null;
        if(this.isMac()) {
            folder=new File(folderLoc);
        } else {
            folder=new File(folderLoc_win);
        }
            if(!folder.exists()) {
                folder.mkdirs();
            }
            FileWriter file;
            PrintWriter output;
        try {
            file=new FileWriter(savFile_txt, true);
            output=new PrintWriter(file);
            if(ess==null) {
                output.println("0");
                output.println("0");
                output.println("0");
                
                output.println("0");
                output.println("0");
                output.println("0");
                
                output.println("0");
                output.println("0");
                output.println("0");
                
            } else {
                for(int x=0;x<ess.length;x++){
                    output.println(""+ess[x]);
                }
            }
            output.close();
            file.close();
            
            savFile_txt.renameTo(savFile_sav);
            ess=new int[9];
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    private boolean isMac() {
        String os=System.getProperty("os.name").toLowerCase();
        if("mac".equals(os.substring(0,3)))
        {
         return true;
        }
        return false;
        
    }
}
