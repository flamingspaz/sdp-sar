package sarsystem.Models;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PDAModel {

    private static boolean[] drives = new boolean[26];
    private String IDList = "";

    // http://stackoverflow.com/questions/3831825/detect-usb-drive-in-java
    public boolean isPDAPluggedIn() {
        boolean exists = false;
        // check for drive from A - Z
        for (int i = 0; i < 26; i++) {
            drives[i] = checkForDrive((char) (i + 'A') + ":/");
        }
        // list Drives
        for (int i = 0; i < 26; i++) {
            if (drives[2] == true) {
                drives[2] = false; // this is not the drive you're looking for (C:/)                
            } else if (drives[i] == true) {
                // create path for the file
                String path = (char) (i + 'A') + ":/" + "/saveData.txt";
                if (new File(path).exists()){ // check if the file containing studentIDs exists
                    try {
                    readFile(path);
                    }
                    catch (IOException io){
                        System.out.println("File not found! : " + io);
                    }
                    exists = true;
                    break;
                }
                else {
                    exists = false;
                }
                }
        }
        return exists;
    }

    // file reader
    private String readFile(String path) throws IOException {
        // read file from PDA
        String content = "";

        File file = new File(path);
        FileReader reader = null;

        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                //file.delete(); // delete file after downloading content
                reader.close();
            }
        }
        setIDList(content);
        return content;
    }

    public String[] getIDList(){
        String[] list = IDList.split(",\\ ", -1);
        for (int i = 0; i < list.length; i++) {
            if (!list[i].equals("")){
            list[i] = list[i].substring(1, list[i].length() - 1);
            }
        }
        return list;
    }
    
    public String setIDList(String IDList){
        this.IDList = IDList;
        return IDList;
    }
    
    // check if directory exists
    private static boolean checkForDrive(String dir) {
        return new File(dir).exists();
    }
}
