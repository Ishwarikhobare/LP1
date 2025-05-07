import java.io.*;
public class AssemblerPass2 {
    public static void main(String[] args) throws Exception {
        BufferedReader inputReader = new BufferedReader(new FileReader("intermediateCode_A1.txt"));
        BufferedReader symReader = new BufferedReader(new FileReader("SymTab_A1.txt"));
        BufferedReader litReader = new BufferedReader(new FileReader("LitTab_A1.txt"));
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter("machine_code_A2.txt"));
        
        // Load SYMTAB and LITTAB into memory for faster lookup
        String[] symtab = symReader.lines().toArray(String[]::new);
        String[] littab = litReader.lines().toArray(String[]::new);
        
        String inputLine;
        
        while ((inputLine = inputReader.readLine()) != null) {
            String[] tokens = inputLine.split("\\)\\(");
            StringBuilder outputLine = new StringBuilder();
            
            for (String token : tokens) {
                token = token.replaceAll("[\\(\\)]", ""); // Remove parentheses
                
                // Process symbols (S,x)
                if (token.startsWith("S,")) {
                    String[] parts = token.split(",")[1].trim().split("\\+");  // Split by '+' for expressions
                    int symIndex = Integer.parseInt(parts[0]) - 1;  // Get the symbol index
                    int additionalOffset = 0;
                    
                    // Check if there's an arithmetic offset like '+3'
                    if (parts.length > 1) {
                        additionalOffset = Integer.parseInt(parts[1].trim());
                    }
                    
                    if (symIndex >= 0 && symIndex < symtab.length) {
                        String[] symTokens = symtab[symIndex].split("\t");
                        if (symTokens.length > 1) {
                            int address = Integer.parseInt(symTokens[1]);
                            address += additionalOffset;  // Add the offset to the address
                            token = getFormattedMachineCode("S", address);
                        }
                    }
                }
                // Process literals (L,x)
                else if (token.startsWith("L,")) {
                    int litIndex = Integer.parseInt(token.split(",")[1].trim()) - 1;
                    if (litIndex >= 0 && litIndex < littab.length) {
                        String[] litTokens = littab[litIndex].split("\t");
                        if (litTokens.length > 1) {
                            int address = Integer.parseInt(litTokens[1]);
                            token = getFormattedMachineCode("L", address);
                        }
                    }
                }
                outputLine.append(token.replace(",", "")).append(" ");
            }
            
            // Remove any remaining alphabetic characters (e.g., opcodes)
            outputLine = new StringBuilder(outputLine.toString().replaceAll("[A-Za-z]", ""));
            outputWriter.write(outputLine.toString().trim());
            outputWriter.newLine();
        }
        
        inputReader.close();
        symReader.close();
        litReader.close();
        outputWriter.close();
    }
    
    // Formats machine code by combining the instruction and address
    private static String getFormattedMachineCode(String instruction, int address) {
        return instruction + " " + String.format("%04d", address); // Zero-padded 4-digit address
    }
}
