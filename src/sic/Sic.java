package sic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Sic {

    public static void main(String[] args) throws IOException {
        LinkedList<String> label = new LinkedList<>() ;
        LinkedList<String> Command = new LinkedList<>() ;
        LinkedList<String> Var = new LinkedList<>() ;
        
        File file = new File("C:\\Users\\Lenovo-win10\\Desktop\\test.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        
        String st;
        while ((st = br.readLine()) != null) {
            String[] parts = st.split(" ");
            label.add(parts[0]);
            Command.add(parts[1]);
            Var.add(parts[2]);
            

        }
        for (int i=0 ; i<Command.size();i++){
            System.out.println(label.get(i)+"  "+Command.get(i)+"  "+Var.get(i));
        }
    }
}

    

