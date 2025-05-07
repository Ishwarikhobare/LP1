import java.io.*;
import java.util.*;

class MacroPass1 {
    static String mnt[][] = new String[5][3]; // Assuming 5 macros in 1 program
    static String ala[][] = new String[10][2]; // Assuming 10 arguments in total
    static String mdt[][] = new String[20][1]; // Assuming 20 lines of code (LOC) in total
    static int mntc = 0, mdtc = 0, alac = 0;

    public static void main(String args[]) {
        initialize(); // Call to initialize data
        pass1();
        System.out.println("Macro Name Table (MNT)");
        display(mnt, mntc, 3);
        System.out.println("Argument List Array (ALA) for Pass 1");
        display(ala, alac, 2);
        System.out.println("Macro Definition Table (MDT)");
        display(mdt, mdtc, 1);
    }

    // Initialize the macro tables (MNT, ALA, MDT)
    public static void initialize() {
        // You can populate the MNT, ALA, and MDT here if needed
        // For example, you might want to load from a file or hardcode some values
    }

    static void pass1() {
        String s;
        try {
            BufferedReader inp = new BufferedReader(new FileReader("input_A3.txt"));
            while ((s = inp.readLine()) != null) {
                if (s.equalsIgnoreCase("MACRO")) {
                    // Read the macro name and its parameters
                    String macroDef = inp.readLine();
                    StringTokenizer st = new StringTokenizer(macroDef);
                    String macroName = st.nextToken();
                    
                    // Store in MNT
                    mnt[mntc][0] = String.valueOf(mntc + 1); // MNT index
                    mnt[mntc][1] = macroName; // Macro Name
                    mnt[mntc][2] = String.valueOf(mdtc + 1); // MDT entry
                    mntc++;

                    // Read arguments, if any
                    if (st.hasMoreTokens()) {
                        String args = st.nextToken();
                        StringTokenizer argTokenizer = new StringTokenizer(args, ",");
                        while (argTokenizer.hasMoreTokens()) {
                            String arg = argTokenizer.nextToken().trim();
                            ala[alac][0] = String.valueOf(alac + 1); // ALA index
                            ala[alac][1] = arg; // Argument Name
                            alac++;
                        }
                    }

                    // Read the macro definition until MEND
                    while (!(s = inp.readLine()).equalsIgnoreCase("MEND")) {
                        // Replace arguments in the macro definition
                        for (int i = 0; i < alac; i++) {
                            String arg = ala[i][1];
                            s = s.replaceAll("&" + arg, "#" + ala[i][0]); // Replace arguments with indices
                        }
                        mdt[mdtc][0] = s; // Store in MDT
                        mdtc++;
                    }
                }
            }
            inp.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to find file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void display(String a[][], int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }
}
