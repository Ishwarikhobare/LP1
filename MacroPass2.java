import java.io.*;
import java.util.*;

class MacroPass2 {
    public static void main(String args[]) {
        pass2();
        System.out.println("Argument List Array (ALA) for Pass 2");
        display(MacroPass1.ala, MacroPass1.alac, 2);
        System.out.println("Note: All tables are displayed here whereas the expanded output is stored in the file pass2_output.txt");
    }

    static void pass2() {
        int alap = 0, index, mdtp, flag = 0;
        String s, temp;
        try {
            BufferedReader inp = new BufferedReader(new FileReader("input_A4.txt")); // Change to input_A4.txt
            File op = new File("MacroPass2_output.txt");
            if (!op.exists()) {
                op.createNewFile();
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(op.getAbsoluteFile()));
            while ((s = inp.readLine()) != null) {
                flag = 0;
                StringTokenizer st = new StringTokenizer(s);
                String str[] = new String[st.countTokens()];
                for (int i = 0; i < str.length; i++) {
                    str[i] = st.nextToken();
                }
                for (int j = 0; j < MacroPass1.mntc; j++) {
                    if (str[0].equals(MacroPass1.mnt[j][1])) {
                        mdtp = Integer.parseInt(MacroPass1.mnt[j][2]);
                        st = new StringTokenizer(str[1], ",");
                        String arg[] = new String[st.countTokens()];
                        for (int i = 0; i < arg.length; i++) {
                            arg[i] = st.nextToken();
                            MacroPass1.ala[alap++][1] = arg[i];
                        }
                        for (int i = mdtp; !(MacroPass1.mdt[i][0].equalsIgnoreCase("MEND")); i++) { 
                            index = MacroPass1.mdt[i][0].indexOf("#");
                            temp = MacroPass1.mdt[i][0].substring(0, index);
                            temp += MacroPass1.ala[Integer.parseInt("" + MacroPass1.mdt[i][0].charAt(index + 1))][1];
                            output.write(temp);
                            output.newLine();
                        }
                        flag = 1;
                    }
                }
                if (flag == 0) { // When it is not a macro
                    output.write(s);
                    output.newLine();
                }
            }
            output.close();
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
