
import java.util.Scanner;

public class ComplaintPriorityDP {

    public static void prioritizeComplaints() {

        Scanner scanner = new Scanner(System.in);

        String[] complaintIds = {"C1", "C2", "C3", "C4", "C5"};
        int[] processingTime = {2, 1, 3, 1, 2};
        int[] priority = {80, 50, 90, 40, 70};

        System.out.print("Enter available processing time: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
            return;
        }

        int maxTime = scanner.nextInt();
        scanner.nextLine();

        if (maxTime < 0) {
            System.out.println("Processing time cannot be negative.");
            return;
        }

        int n = complaintIds.length;
        int[][] dp = new int[n + 1][maxTime + 1];

        for (int i = 1; i <= n; i++) {
            for (int time = 0; time <= maxTime; time++) {

                if (processingTime[i - 1] <= time) {
                    dp[i][time] = Math.max(
                        dp[i - 1][time],
                        priority[i - 1] +
                        dp[i - 1][time - processingTime[i - 1]]
                    );
                } else {
                    dp[i][time] = dp[i - 1][time];
                }
            }
        }

        System.out.println("\nSelected complaints:");
        int time = maxTime;
        int totalTime = 0;

        for (int i = n; i > 0; i--) {
            if (dp[i][time] != dp[i - 1][time]) {
                System.out.println(
                    complaintIds[i - 1] +
                    " | Priority: " + priority[i - 1] +
                    " | Processing time: " + processingTime[i - 1]
                );

                time -= processingTime[i - 1];
                totalTime += processingTime[i - 1];
            }
        }

        System.out.println("--------------------------------");
        System.out.println("Total processing time: " + totalTime);
        System.out.println("Maximum priority: " + dp[n][maxTime]);
        System.out.println("Algorithm: 0/1 Knapsack DP");
        System.out.println("Time complexity: O(n × W)");
    }
}
