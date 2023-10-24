import java.util.*;

class Process {
    public int id, arrivalTime, burstTime, remainingTime;

    Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SJF_with_premption {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of processes:");
        int n = sc.nextInt();
        ArrayList<Process> processes = new ArrayList<>();

        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];
        boolean[] completed = new boolean[n];

        System.out.println("Enter arrival time and burst time for each process:");

        for (int i = 0; i < n; i++) {
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }

        int currentTime = 0;
        int completedCount = 0;
        Process currentProcess = null;

        while (completedCount < n) {
            for (Process p : processes) {
                if (!completed[p.id - 1] && p.arrivalTime <= currentTime &&
                        (currentProcess == null || p.remainingTime < currentProcess.remainingTime)) {
                    currentProcess = p;
                }
            }

            if (currentProcess == null) {
                currentTime++;
            } else {
                currentProcess.remainingTime--;

                if (currentProcess.remainingTime == 0) {
                    completedCount++;
                    completed[currentProcess.id - 1] = true;
                    turnaroundTime[currentProcess.id - 1] = currentTime - currentProcess.arrivalTime + 1;
                    waitingTime[currentProcess.id - 1] = turnaroundTime[currentProcess.id - 1]
                            - currentProcess.burstTime;
                    currentProcess = null;
                }

                currentTime++;
            }
        }

        double avgWaitingTime = Arrays.stream(waitingTime).average().getAsDouble();
        double avgTurnaroundTime = Arrays.stream(turnaroundTime).average().getAsDouble();

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);

        sc.close();
    }
}
