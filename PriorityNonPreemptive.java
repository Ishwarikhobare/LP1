import java.util.Scanner;

// Class representing a Process with attributes for Priority Scheduling
class Process {
    int pid, burstTime, priority, waitingTime, turnaroundTime;

    public Process(int pid, int burstTime, int priority) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class PriorityNonPreemptive {  // Ensure this class matches the file name
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];

        // Input burst time and priority for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter burst time and priority for process " + (i + 1) + ": ");
            int bt = sc.nextInt();
            int priority = sc.nextInt();
            processes[i] = new Process(i + 1, bt, priority);
        }

        // Sort processes by priority (non-preemptive scheduling)
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (processes[j].priority > processes[j + 1].priority) {
                    // Swap if the priority of the current process is higher than the next one
                    Process temp = processes[j];
                    processes[j] = processes[j + 1];
                    processes[j + 1] = temp;
                }
            }
        }

        int totalTime = 0, totalWT = 0, totalTAT = 0;

        // Calculate waiting time and turnaround time for each process
        for (Process p : processes) {
            p.waitingTime = totalTime;  // Waiting time is the total time the process waited
            totalTime += p.burstTime;  // Update the total time spent so far
            p.turnaroundTime = p.waitingTime + p.burstTime;  // Turnaround time = waiting time + burst time
            totalWT += p.waitingTime;  // Accumulate total waiting time
            totalTAT += p.turnaroundTime;  // Accumulate total turnaround time
        }

        // Print Gantt Chart
        System.out.println("Gantt Chart: ");
        for (Process p : processes) {
            System.out.print("P" + p.pid + " ");
        }
        System.out.println("\n");

        // Print process details: burst time, priority, waiting time, and turnaround time
        System.out.println("Process\tBurst\tPriority\tWaiting\tTurnaround");
        for (Process p : processes) {
            System.out.println("P" + p.pid + "\t" + p.burstTime + "\t" + p.priority + "\t\t" + p.waitingTime + "\t\t" + p.turnaroundTime);
        }

        // Print average waiting time and average turnaround time
        System.out.println("Average Waiting Time: " + (totalWT / (float) n));
        System.out.println("Average Turnaround Time: " + (totalTAT / (float) n));

        sc.close();
    }
}
