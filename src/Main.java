
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Start the website API
        try {
            ComplaintTrackerServer.start();
        } catch (java.io.IOException e) {
            System.out.println(
                "Website server could not start: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n====================================");
            System.out.println("        COMPLAINT TRACKER");
            System.out.println("====================================");
            System.out.println("1. Search Complaints (KMP)");
            System.out.println("2. Prioritize Complaints (Dynamic Programming)");
            System.out.println("3. Assign Complaints (Network Flow)");
            System.out.println("4. Select Support Teams (Greedy Set Cover)");
            System.out.println("5. Exit");
            System.out.println("====================================");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter a number from 1 to 5.");
                scanner.nextLine();
                continue;
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    ComplaintTrackerKMP.searchComplaints();
                    break;

                case 2:
                    ComplaintPriorityDP.prioritizeComplaints();
                    break;

                case 3:
                    ComplaintAssignmentFlow.assignComplaints();
                    break;

                case 4:
                    ComplaintSetCover.selectSupportTeams();
                    break;

                case 5:
                    System.out.println(
                        "Exiting Complaint Tracker. Goodbye!");
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Enter a number from 1 to 5.");
            }

        } while (choice != 5);

        scanner.close();
    }
}
