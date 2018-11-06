package sic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sic {

    public static void main(String[] args) {
        LinkedList<String> label = new LinkedList<>();
        LinkedList<String> inst = new LinkedList<>();
        LinkedList<String> var = new LinkedList<>();
        LinkedList<String> Adress = new LinkedList<>();
        LinkedList<String> Simple = new LinkedList<>();
        LinkedList<String> SimpleA = new LinkedList<>();

        File file = new File("/home/ahmed/Desktop/test.txt");
        //READ THE FILE 
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sic.class.getName()).log(Level.SEVERE, null, ex);
        }
        //split 
        String st;
        try {
            while ((st = br.readLine()) != null) {
                String[] parts = st.split(" ");
                if (parts[0].equals("	")) {
                    label.add(";");
                } else {
                    label.add(parts[0]);
                    Simple.add(parts[0]);
                }
                if (parts[1].equals("	")) {
                    inst.add(";");
                } else {
                    inst.add(parts[1]);
                }
                if (parts[2].equals(" ")) {
                    var.add(";");
                } else {
                    var.add(parts[2]);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Sic.class.getName()).log(Level.SEVERE, null, ex);
        }
        //get the addres
        int x;
        Adress.add(var.get(0));
        Adress.add(var.get(0));
        converter con = new converter();
        converter.initialize();
        //symble table 
        String[][] SymbleTable = new String[Simple.size() + 2][2];

        for (int i = 0; i < Simple.size(); i++) {
            SymbleTable[i][0] = Simple.get(i);
        }

        int j, total, curr, prev = 0;
        for (j = 0; j < label.size(); j++) {
            if (label.get(j).equals("Start")) {
                Adress.add(var.get(j));
                total = Integer.parseInt(var.get(j), 16);
                prev = total;
            } else if (inst.get(j).equals("RESW")) {
                curr = 3 * Integer.valueOf(var.get(j));
                total = curr + prev;
                Adress.add(Integer.toHexString(total));
                prev = total;
            } else if (inst.get(j).equals("RESB")) {
                curr = Integer.valueOf(var.get(j));
                total = curr + prev;
                Adress.add(Integer.toHexString(total));
                prev = total;
            } else if (inst.get(j).equals("BYTE")) {
                if (label.get(j).charAt(0) == 'X') {
                    curr = (label.get(j).length() - 3) / 2;
                    total = curr + prev;
                    Adress.add(Integer.toHexString(total));
                    prev = total;
                } else if (inst.get(j).charAt(0) == 'C') {
                    curr = label.get(j).length() - 3;
                    total = curr + prev;
                    Adress.add(Integer.toHexString(total));
                    prev = total;
                }
            } else {
                if (var.get(j).contains(",")) {
                    String[] word = inst.get(j).split(",");
                    curr = 3 * word.length;
                    total = curr + prev;
                    System.out.println(Integer.toHexString(total));
                    Adress.add(Integer.toHexString(total));
                    prev = total;
                } else {
                    curr = 3;
                    total = curr + prev;
                    Adress.add(Integer.toHexString(total));
                    prev = total;
                }
            }
        }
        SimpleA.add("0");
        for (int q = 0; q < label.size(); q++) {
            for (int y = 0; y < var.size(); y++) {
                if (!label.get(q).equals(";")) {
                    if (label.get(q).equals(var.get(y))) {
                        if (!SimpleA.getLast().equals(Adress.get(q))) {
                            SimpleA.add(Adress.get(q));
                        }
                    }
                }
            }
        }
        for (int u = 0; u < SimpleA.size(); u++) {
            SymbleTable[u][1] = SimpleA.get(u);
        }
/////////////////////////////////////////////////////////////////////////////////
        String[][] opcode = new String[var.size()][3];
        for (int i = 0; i < inst.size(); i++) {
            for (int k = 0; k < 59; k++) {
                if (inst.get(i).trim().equals(con.find(k))) {
                    opcode[i][0] = con.getopcode(k, 2);
                }
            }
        }
        for (int c = 0; c < label.size(); c++) {
            if (label.get(c).equals("START")
                    || label.get(c).equals("END")
                    || inst.get(c).equals("RESB")
                    || inst.get(c).equals("RESW")) {
                opcode[c][0] = "No";
                opcode[c][1] = "object code";
                opcode[c][2] = "";

            } else if (inst.get(c).equals("WORD")) {
                if (var.get(c).contains(",")) {
                    String[] word = var.get(c).split(",");
                    opcode[c][0] = "00";
                    opcode[c][1] = "0";
                    String q = Integer.toHexString(Integer.parseInt(word[0]));
                    String e = Integer.toHexString(Integer.parseInt(word[1]));
                    String r = Integer.toHexString(Integer.parseInt(word[2]));
                    String g = q + "," + e + "," + r;
                    opcode[c][2] = g;
                    System.out.println(g);
                } else {
                    opcode[c][0] = "00";
                    opcode[c][1] = "0";
                    opcode[c][2] = Integer.toHexString(Integer.parseInt(var.get(c)));
                }
            } else if (inst.get(c).equals("BYTE")) {
                if (label.get(c).charAt(0) == 'x' || label.get(c).charAt(0) == 'X') {
                    String[] s = new String[3];
                    s = label.get(c).split("'");
                    opcode[c][0] = "00";
                    opcode[c][1] = "0";
                    opcode[c][2] = Integer.toHexString(Integer.parseInt(s[1]));
                    //  System.out.println(opcode[c][2]);
                }
            } else {
                opcode[c][1] = "0";
                for (int s = 0; s < Simple.size(); s++) {
                    for (int w = 0; w < var.size(); w++) {
                        //   System.out.println(var.get(w)+"   before cond "+SymbleTable[s][0]);

                        if (var.get(w).contains(SymbleTable[s][0].trim())) {
                            opcode[c][2] = SymbleTable[s][1];
                            //  System.out.println("object code " + opcode[c][2]);
                            //    System.out.println(opcode[c][0]+opcode[c][1]+opcode[c][2]);
                            break;

                        }
                    }
                }
            }
        }

        String HRec[] = new String[3];
        HRec[0] = label.getFirst();
        HRec[1] = var.getFirst();
        HRec[2] = Integer.toHexString(Integer.parseInt(Adress.getLast(), 16) - Integer.parseInt(Adress.getFirst(), 16));
        String ERec = var.getFirst();
        System.out.println("H." + HRec[0] + " Start " + HRec[1] + " lenght" + HRec[2]);

        String[][] TRec = new String[10][3];
        int c = 0;
        for (int a = 0; a < inst.size(); a++) {
            if (inst.get(a).equals("RESW") || inst.get(a).equals("RESB")) {
            } else if (c >= 10) {
                c = 0;
            } else {
                System.out.print("T." + opcode[a][0] + opcode[a][1] + opcode[a][2] + "   ");
                c++;

            }
        }
        System.out.println("\n" + "E." + ERec);
    }
}
