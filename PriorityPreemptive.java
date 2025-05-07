import java.util.Scanner;

public class PriorityPreemptive {

    // Define the Process class
    static class Process {
        int id, burst, priority, arrival, waitingTime, turnaroundTime;
        int remainingBurst;

        public Process(int id, int burst, int priority, int arrival) {
            this.id = id;
            this.burst = burst;
            this.priority = priority;
            this.arrival = arrival;
            this.remainingBurst = burst;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        Process[] processes = new Process[n];

        // Input arrival time, burst time, and priority for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time, burst time, and priority for process " + (i + 1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            int priority = sc.nextInt();
            processes[i] = new Process(i + 1, bt, priority, at);
        }

        int completed = 0, time = 0;
        String ganttChart = "";

        // Preemptive Priority Scheduling
        while (completed < n) {
            int idx = -1;
            int highestPriority = Integer.MAX_VALUE;

            // Find the process with the highest priority (lowest priority number) that has arrived
            for (int i = 0; i < n; i++) {
                if (processes[i].arrival <= time && processes[i].remainingBurst > 0 && processes[i].priority < highestPriority) {
                    highestPriority = processes[i].priority;
                    idx = i;
                }
            }

            // If a process is found, execute it for 1 unit of time
            if (idx != -1) {
                processes[idx].remainingBurst--;
                ganttChart += "P" + processes[idx].id + " ";

                // If the process completes, calculate its turnaround time and waiting time
                if (processes[idx].remainingBurst == 0) {
                    completed++;
                    processes[idx].turnaroundTime = time + 1 - processes[idx].arrival;
                    processes[idx].waitingTime = processes[idx].turnaroundTime - processes[idx].burst;
                }
            } else {
                ganttChart += "idle ";
            }

            time++;
        }

        // Print Gantt Chart
        System.out.println("Gantt Chart: " + ganttChart);

        // Calculate and print average waiting time and turnaround time
        int totalWT = 0, totalTAT = 0;
        System.out.println("Process\tArrival\tBurst\tPriority\tWaiting\tTurnaround");
        for (Process p : processes) {
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
            System.out.println("P" + p.id + "\t" + p.arrival + "\t" + p.burst + "\t" + p.priority + "\t\t" + p.waitingTime + "\t" + p.turnaroundTime);
        }

        // Print average waiting time and turnaround time
        System.out.println("Average Waiting Time: " + (totalWT / (float) n));
        System.out.println("Average Turnaround Time: " + (totalTAT / (float) n));

        sc.close();
    }
}
