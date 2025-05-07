import java.util.Scanner;

class firstfit{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take input for block sizes
        System.out.print("Enter number of memory blocks: ");
        int m = scanner.nextInt();
        int[] blockSize = new int[m];
        System.out.println("Enter the sizes of each block:");
        for (int i = 0; i < m; i++) {
            System.out.print("Block " + (i + 1) + ": ");
            blockSize[i] = scanner.nextInt();
        }

        // Take input for process sizes
        System.out.print("\nEnter number of processes: ");
        int n = scanner.nextInt();
        int[] processSize = new int[n];
        System.out.println("Enter the sizes of each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            processSize[i] = scanner.nextInt();
        }

        // Call the First Fit function with user-provided sizes
        implementFirstFit(blockSize, m, processSize, n);
        scanner.close();
    }

    static void implementFirstFit(int[] blockSize, int blocks, int[] processSize, int processes) {
        int[] allocate = new int[processes];
        int[] occupied = new int[blocks];

        // Initialize allocations to -1 (not allocated)
        for (int i = 0; i < processes; i++) {
            allocate[i] = -1;
        }

        for (int i = 0; i < blocks; i++) {
            occupied[i] = 0;
        }

        // First Fit Allocation
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < blocks; j++) {
                if (occupied[j] == 0 && blockSize[j] >= processSize[i]) {
                    allocate[i] = j;
                    occupied[j] = 1;
                    break;
                }
            }
        }

        // Print the allocation results
        System.out.println("\nProcess No.\tProcess Size\tBlock No.");
        for (int i = 0; i < processes; i++) {
            System.out.print((i + 1) + "\t\t\t" + processSize[i] + "\t\t\t");
            if (allocate[i] != -1) {
                System.out.println(allocate[i] + 1);
            } else {
                System.out.println("Not Allocated");
            }
        }
    }
}
