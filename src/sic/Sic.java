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

        File file = new File("/home/ahmed/Desktop/test.txt");

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
        Adress.add(Var.get(0));

        for (int i = 0; i < Command.size(); i++) {
            if (!"RESW".equals(Command.get(i).toUpperCase())
                    && !"RESB".equals(Command.get(i).toUpperCase())
                    && !"BYTE".equals(Command.get(i).toUpperCase())) {
                Adress.add(Integer.toHexString(3 + Integer.decode("0x" + (String) Adress.getLast())));
            } else if ("RESW".equals(Command.get(i).toUpperCase())) {
                x = 3 * Integer.parseInt(Var.get(i));
                Adress.add(Integer.toHexString(x + Integer.decode("0x" + (String) Adress.getLast())));

            } else if ("RESB".equals(Command.get(i).toUpperCase())) {
                x = 1 * Integer.parseInt(Var.get(i));
                Adress.add(Integer.toHexString(x + Integer.decode("0x" + (String) Adress.getLast())));
            } else if ("BYTE".equals(Command.get(i).toUpperCase())) {
                if (Command.get(i).startsWith("X")) {
                    int y = Command.get(i).length() / 2;
                    x = y * Integer.parseInt(Var.get(i));
                    Adress.add(Integer.toHexString(x + Integer.decode("0x" + (String) Adress.getLast())));

                }
            }
        }

        converter con = new converter();
        converter.initialize();
        String[][] opcode = new String[Command.size()][3];

        for (int i = 1; i < Command.size(); i++) {
            for (int j = 0; j<59; j++) {
                if (Command.get(i).equals(con.find(j))) {
                    opcode[i][0] = con.getopcode(j, 2);
                }
            }
        }

        for (int i = 0; i < 19; i++) {
            System.out.println(Adress.get(i) + "        " + label.get(i) + "        " + Command.get(i) + "      " + Var.get(i) + "       " + opcode[i][0]);
        }

    }
}
