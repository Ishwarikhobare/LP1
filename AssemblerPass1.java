import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

class AssemblerPass1 {
    static Scanner in = new Scanner(System.in);
    static String[] is = {
        "STOP", "ADD", "SUB", "MULT", "MOVER", "MOVEM", "COMP", "BC", "DIV", "READ", "PRINT"
    };
    static String[] ad = { "START", "END", "ORIGIN", "EQU", "LTORG" };
    static String[] dl = { "DC", "DS" };
    static String[] cc = { "LT", "LE", "EQ", "GT", "GE", "ANY" };
    static int symcounter = 0;
    static int litcounter = 0;
    static String[][] sym = new String[100][2];
    static String[][] lit = new String[100][2];
    static String[][] ptab = new String[100][2];

    public static void main(String[] args) throws Exception {
        int locate = 0;
        int litcount = 0;

        BufferedReader reader = new BufferedReader(new FileReader("input_A1.asm")); // For input file
        BufferedWriter writer = new BufferedWriter(new FileWriter("intermediateCode_A1.txt")); // For intermediate output
        BufferedWriter writer1 = new BufferedWriter(new FileWriter("SymTab_A1.txt")); // For symbol table
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("LitTab_A1.txt")); // For literal table

        String st;
        String y, prev = null;
        int stp = 0;
        int k = 0;
        String buffer = ""; // For intermediate code
        String buffer1 = ""; // For symbol table
        String buffer2 = ""; // For literal table

        while ((st = reader.readLine()) != null) {
            int isflag = 0;
            k++;
            StringTokenizer splitted = new StringTokenizer(st);
            String ans = "";

            while (splitted.hasMoreTokens()) {
                y = splitted.nextToken();

                if (y.equals("START")) {
                    locate = Integer.parseInt(splitted.nextToken());
                    ans = "(AD,01)(C," + locate + ")";
                    break;
                } else {
                    if (searchis(y)) {
                        if (y.equals("STOP")) {
                            stp = 1;
                        }
                        ans += "(IS," + indexis(y) + ")";
                        isflag = 1;
                        locate += 1;
                    } else if (searchad(y)) {
                        if (y.equals("LTORG")) {
                            locate += litcount;
                            ans = "(AD,05)\n";
                            while (litcount > 0) {
                                lit[litcounter - litcount][1] = Integer.toString(locate - litcount);
                                int len = lit[litcounter - litcount][0].length();
                                String temp = lit[litcounter - litcount][0].substring(2, len - 1);
                                ans += "(DL,02)(C," + temp + ")";
                                litcount--;
                                if (litcount != 0) {
                                    ans += "\n";
                                }
                            }
                        }

                        if (y.equals("ORIGIN")) {
                            y = splitted.nextToken();
                            String[] words = y.split("\\+");
                            int location = Integer.parseInt(sym[indexsym(words[0])][1]);
                            locate = location + Integer.parseInt(words[1]);
                            ans = "(AD,03)(S," + (indexsym(words[0]) + 1) + ")+" + words[1];
                        }

                        if (y.equals("END") && litcount != 0) {
                            locate += litcount;
                            ans = "(AD,02)\n";
                            while (litcount > 0) {
                                lit[litcounter - litcount][1] = Integer.toString(locate - litcount);
                                int len = lit[litcounter - litcount][0].length();
                                String temp = lit[litcounter - litcount][0].substring(2, len - 1);
                                ans += "(DL,02)(C," + temp + ")\n";
                                litcount--;
                                if (litcount != 0) {
                                    ans += "\n";
                                }
                            }
                        }

                        if (y.equals("EQU")) {
                            int temp = indexsym(splitted.nextToken());
                            y = prev;
                            sym[indexsym(y)][1] = sym[temp][1];
                            ans = "";
                        }
                    } else if (searchdl(y)) {
                        if (y.equals("DS")) {
                            ans = "(DL,1)(C," + splitted.nextToken() + ")";
                        }
                        if (y.equals("DC")) {
                            ans = "(DL,2)(C," + splitted.nextToken() + ")";
                        }
                        locate += 1;
                    } else {
                        prev = y;
                        char[] x = y.toCharArray();
                        if (x[0] == '=') {
                            int z = litcounter;
                            ans += "(L," + (z + 1) + ")";
                            lit[litcounter++][0] = y;
                            litcount++;
                        } else if (y.equals("AREG")) {
                            ans += "(R,1)";
                        } else if (y.equals("BREG")) {
                            ans += "(R,2)";
                        } else if (y.equals("CREG")) {
                            ans += "(R,3)";
                        } else if (y.equals("DREG")) {
                            ans += "(R,4)";
                        } else if (searchcc(y)) {
                            ans += "(" + (indexcc(y) + 1) + ")";
                        } else {
                            if (!searchsym(y) && isflag == 0 && stp == 0) {
                                sym[symcounter][0] = y;
                                sym[symcounter++][1] = Integer.toString(locate);
                                ans += "(S," + (indexsym(y) + 1) + ")";
                                if (splitted.hasMoreTokens()) {
                                    ans = "";
                                }
                            } else if (!searchsym(y) && isflag == 1 && stp == 0) {
                                sym[symcounter++][0] = y;
                                ans += "(S," + (indexsym(y) + 1) + ")";
                            } else if (searchsym(y) && isflag == 0) {
                                sym[indexsym(y)][1] = Integer.toString(locate);
                                ans += "(S," + (indexsym(y) + 1) + ")";
                                if (splitted.hasMoreTokens()) {
                                    ans = "";
                                }
                                prev = y;
                            } else {
                                if (!splitted.hasMoreTokens()) {
                                    ans += "(S," + (indexsym(y) + 1) + ")";
                                }
                                continue;
                            }
                        }
                    }
                }
            }
            ans = ans + "\n";
            buffer += ans;
        }

        System.out.println(buffer); // Output intermediate code
        String ans1 = ""; // Symbol table
        for (int i = 0; i < symcounter; i++) {
            ans1 += sym[i][0] + "\t" + sym[i][1] + "\n";
        }
        buffer1 += ans1;

        String ans2 = ""; // Literal table
        for (int i = 0; i < litcounter; i++) {
            ans2 += lit[i][0] + "\t" + lit[i][1] + "\n";
        }
        buffer2 += ans2;

        // Write output to files
        writer.write(buffer);
        writer1.write(buffer1);
        writer2.write(buffer2);
        
        reader.close();
        writer.close();
        writer1.close();
        writer2.close();

        System.out.println("Program finished.");
    }

    // Utility methods
    public static boolean searchis(String s) {
        for (int i = 0; i < is.length; i++) {
            if (is[i].equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean searchad(String s) {
        for (int i = 0; i < ad.length; i++) {
            if (ad[i].equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean searchdl(String s) {
        for (int i = 0; i < dl.length; i++) {
            if (dl[i].equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean searchsym(String s) {
        if (s.equals("BREG") || s.equals("AREG") || s.equals("CREG") || s.equals("DREG") ||
            s.equals(",") || s.equals("LE") || s.equals("LT") || s.equals("ANY") || 
            s.equals("EQ") || s.equals("GT") || s.equals("GE")) {
            return true;
        }
        for (int i = 0; i < symcounter; i++) {
            if (sym[i][0].equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean searchcc(String s) {
        for (int i = 0; i < cc.length; i++) {
            if (cc[i].equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static int indexsym(String s) {
        for (int i = 0; i < symcounter; i++) {
            if (sym[i][0].equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public static int indexis(String s) {
        for (int i = 0; i < is.length; i++) {
            if (is[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public static int indexcc(String s) {
        for (int i = 0; i < cc.length; i++) {
            if (cc[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }
}
