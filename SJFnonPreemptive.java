import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

class SJFNProcess {
    int pid, burstTime, arrivalTime, waitingTime, turnaroundTime;

    public SJFNProcess(int pid, int burstTime, int arrivalTime) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }
}

public class SJFnonPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        SJFNProcess[] processes = new SJFNProcess[n];

        // Input arrival time and burst time for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            processes[i] = new SJFNProcess(i + 1, bt, at);
        }

        // Sort by arrival time first, then by burst time for processes that arrive at the same time
        Arrays.sort(processes, Comparator.comparingInt((SJFNProcess p) -> p.arrivalTime)
                                         .thenComparingInt(p -> p.burstTime));

        int totalTime = 0, totalWT = 0, totalTAT = 0;

        for (int i = 0; i < n; i++) {
            if (totalTime < processes[i].arrivalTime) {
                totalTime = processes[i].arrivalTime; // Adjust if no process has arrived yet
            }

            // Calculate waiting time
            processes[i].waitingTime = totalTime - processes[i].arrivalTime;
            
            // Update total time by adding burst time
            totalTime += processes[i].burstTime;
            
            // Calculate turnaround time
            processes[i].turnaroundTime = processes[i].waitingTime + processes[i].burstTime;
            
            totalWT += processes[i].waitingTime;
            totalTAT += processes[i].turnaroundTime;
        }

        // Output Gantt Chart
        System.out.println("\nGantt Chart: ");
        for (SJFNProcess p : processes) {
            System.out.print("P" + p.pid + " ");
        }
        System.out.println("\n");

        // Output process details
        System.out.println("Process\tArrival\tBurst\tWaiting\tTurnaround");
        for (SJFNProcess p : processes) {
            System.out.println("P" + p.pid + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" +
                               p.waitingTime + "\t" + p.turnaroundTime);
        }

        // Calculate and print averages
        System.out.println("\nAverage Waiting Time: " + (totalWT / (float) n));
        System.out.println("Average Turnaround Time: " + (totalTAT / (float) n));

        sc.close();
    }
}
