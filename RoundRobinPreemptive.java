import java.util.Scanner;

public class RoundRobinPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Input number of processes and quantum
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        int[] burstTime = new int[n];
        int[] remainingTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] arrivalTime = new int[n];
        
        System.out.print("Enter time quantum: ");
        int quantum = sc.nextInt();
        
        // Input burst time and arrival time for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i + 1) + ": ");
            arrivalTime[i] = sc.nextInt();
            burstTime[i] = sc.nextInt();
            remainingTime[i] = burstTime[i];
        }
        
        int currentTime = 0;
        int completedProcesses = 0;
        String ganttChart = "";

        // Round Robin scheduling
        while (completedProcesses < n) {
            boolean found = false;
            for (int i = 0; i < n; i++) {
                if (remainingTime[i] > 0 && arrivalTime[i] <= currentTime) {
                    found = true;
                    if (remainingTime[i] > quantum) {
                        currentTime += quantum;
                        remainingTime[i] -= quantum;
                        ganttChart += "P" + (i + 1) + " ";
                    } else {
                        currentTime += remainingTime[i];
                        ganttChart += "P" + (i + 1) + " ";
                        waitingTime[i] = currentTime - burstTime[i] - arrivalTime[i];
                        turnaroundTime[i] = waitingTime[i] + burstTime[i];
                        remainingTime[i] = 0;
                        completedProcesses++;
                    }
                }
            }
            
            // If no process is ready, increase time (CPU idle)
            if (!found) {
                currentTime++;
                ganttChart += "idle ";
            }
        }

        // Output Gantt Chart
        System.out.println("Gantt Chart: " + ganttChart);
        
        // Calculate average waiting time and turnaround time
        float totalWT = 0, totalTAT = 0;
        System.out.println("Process\tArrival\tBurst\tWaiting\tTurnaround");
        for (int i = 0; i < n; i++) {
            totalWT += waitingTime[i];
            totalTAT += turnaroundTime[i];
            System.out.println("P" + (i + 1) + "\t" + arrivalTime[i] + "\t" + burstTime[i] + "\t" +
                               waitingTime[i] + "\t" + turnaroundTime[i]);
        }

        System.out.println("Average Waiting Time: " + (totalWT / n));
        System.out.println("Average Turnaround Time: " + (totalTAT / n));
        sc.close();
    }
}
