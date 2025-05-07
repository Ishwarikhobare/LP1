import java.util.Scanner;

public class WorstFit {

    // Function to allocate memory to empty blocks based on the worst fit algorithm
    static void worstFit(int b_size[], int m, int p_size[], int n) {
        // Stores block id of the block which is allocated to a process
        int allocate[] = new int[n];

        // No block is assigned to any process initially
        for (int i = 0; i < allocate.length; i++)
            allocate[i] = -1;

        // Select each process and find the largest suitable block for it
        for (int i = 0; i < n; i++) {
            // Index of the largest block that can accommodate p_size[i]
            int wstIdx = -1;
            for (int j = 0; j < m; j++) {
                if (b_size[j] >= p_size[i]) {
                    if (wstIdx == -1) {
                        wstIdx = j;
                    } else if (b_size[wstIdx] < b_size[j]) {
                        wstIdx = j;
                    }
                }
            }

            // If we found a block for p_size[i]
            if (wstIdx != -1) {
                // Assign the block to process p[i]
                allocate[i] = wstIdx;

                // Reduce available memory in this block
                b_size[wstIdx] -= p_size[i];
            }
        }

        // Display allocation details
        System.out.println("\nProcess Number\tProcess Size\tBlock Number");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + p_size[i] + "\t\t");
            if (allocate[i] != -1) {
                System.out.print(allocate[i] + 1);  // Display block index (1-based)
            } else {
                System.out.print("Not Allocated");
            }
            System.out.println();
        }
    }

    // Driver code
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input memory block sizes
        System.out.print("Enter the number of memory blocks: ");
        int m = sc.nextInt();
        int b_size[] = new int[m];
        System.out.println("Enter the size of each memory block:");
        for (int i = 0; i < m; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            b_size[i] = sc.nextInt();
        }

        // Input process sizes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        int p_size[] = new int[n];
        System.out.println("Enter the size of each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            p_size[i] = sc.nextInt();
        }

        // Perform worst fit allocation
        worstFit(b_size, m, p_size, n);
        
        sc.close();
    }
}
