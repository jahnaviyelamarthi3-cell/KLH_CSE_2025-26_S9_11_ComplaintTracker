
import java.util.*;

public class ComplaintSetCover {

    public static void selectSupportTeams() {

        String[] complaints = {
            "C1 - Internet not working",
            "C2 - Application login failure",
            "C3 - Payment failure",
            "C4 - Security issue",
            "C5 - Application crashing"
        };

        String[] requiredSkills = {
            "Network", "Software", "Database", "Security", "Software"
        };

        String[] technicians = {
            "Ravi", "Priya", "Arjun", "Meena"
        };

        String[][] technicianSkills = {
            {"Network", "Software"},
            {"Database", "Security"},
            {"Software", "Network"},
            {"Security", "Network"}
        };

        Set<String> uncoveredSkills = new LinkedHashSet<>();

        for (String skill : requiredSkills) {
            uncoveredSkills.add(skill);
        }

        Set<Integer> selectedTechnicians = new LinkedHashSet<>();

        System.out.println("\n--- CO5: Greedy Technician Selection ---");

        System.out.println("\nComplaint requirements:");

        for (int i = 0; i < complaints.length; i++) {
            System.out.println(complaints[i]
                    + " | Required skill: " + requiredSkills[i]);
        }

        while (!uncoveredSkills.isEmpty()) {

            int bestTechnician = -1;
            int bestCoverage = 0;

            for (int i = 0; i < technicians.length; i++) {
                if (selectedTechnicians.contains(i)) {
                    continue;
                }

                int coverage = 0;

                for (String skill : technicianSkills[i]) {
                    if (uncoveredSkills.contains(skill)) {
                        coverage++;
                    }
                }

                if (coverage > bestCoverage) {
                    bestCoverage = coverage;
                    bestTechnician = i;
                }
            }

            if (bestTechnician == -1) {
                System.out.println(
                    "\nSome required skills have no available technician: "
                    + uncoveredSkills);
                return;
            }

            selectedTechnicians.add(bestTechnician);

            System.out.println("\nSelected technician: "
                    + technicians[bestTechnician]);

            for (String skill : technicianSkills[bestTechnician]) {
                if (uncoveredSkills.remove(skill)) {
                    System.out.println("  Covers skill: " + skill);
                }
            }
        }

        System.out.println("\nSelected technicians:");

        for (int index : selectedTechnicians) {
            System.out.println("- " + technicians[index]);
        }

        System.out.println("All required skills are covered.");
        System.out.println("Total technicians selected: "
                + selectedTechnicians.size());
    }
}
