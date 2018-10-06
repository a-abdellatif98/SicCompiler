package sic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import javax.xml.bind.DatatypeConverter;

public class Sic {

    public static void main(String[] args) throws IOException {
        LinkedList<String> label = new LinkedList<>();
        LinkedList<String> Command = new LinkedList<>();
        LinkedList<String> Var = new LinkedList<>();
        LinkedList Adress = new LinkedList<>();

        File file = new File("C:\\Users\\Lenovo-win10\\Desktop\\test.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            String[] parts = st.split(" ");
            label.add(parts[0]);
            Command.add(parts[1]);
            Var.add(parts[2]);

        }
        int x;
        Adress.add(Var.get(0));
        for (int i = 0; i < Command.size(); i++) {
            if (!"RESW".equals(Command.get(i)) || !"RESB".equals(Command.get(i))) {
                Adress.add(Integer.toHexString(3 + Integer.decode("0x" + (String) Adress.getLast())));
            } else if ("RESW".equals(Command.get(i))) {
                x = 3 * Integer.parseInt(Var.get(i));
                Adress.add(Integer.toHexString(x + Integer.decode("0x" + (String) Adress.getLast())));

            } else {
                x = 1 * Integer.parseInt(Var.get(i));
                Adress.add(Integer.toHexString(x + Integer.decode("0x" + (String) Adress.getLast())));

            }
        }

        for (int i = 0; i < Adress.size(); i++) {
            System.out.println(Adress.get(i));
        }

    }
}
