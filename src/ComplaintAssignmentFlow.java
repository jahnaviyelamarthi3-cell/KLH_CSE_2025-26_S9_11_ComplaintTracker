
import java.util.*;

public class ComplaintAssignmentFlow {

    static class Edge {
        int to, reverse, capacity, originalCapacity;

        Edge(int to, int reverse, int capacity) {
            this.to = to;
            this.reverse = reverse;
            this.capacity = capacity;
            this.originalCapacity = capacity;
        }
    }

    static List<Edge>[] graph;

    static void addEdge(int from, int to, int capacity) {
        Edge forward = new Edge(to, graph[to].size(), capacity);
        Edge backward = new Edge(from, graph[from].size(), 0);

        graph[from].add(forward);
        graph[to].add(backward);
    }

    static int bfs(int source, int sink, int[] parentNode,
                   int[] parentEdge) {

        Arrays.fill(parentNode, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        parentNode[source] = source;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int i = 0; i < graph[current].size(); i++) {
                Edge edge = graph[current].get(i);

                if (parentNode[edge.to] == -1 && edge.capacity > 0) {
                    parentNode[edge.to] = current;
                    parentEdge[edge.to] = i;
                    queue.add(edge.to);

                    if (edge.to == sink) {
                        return 1;
                    }
                }
            }
        }

        return 0;
    }

    public static void assignComplaints() {

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

        int source = 0;
        int complaintStart = 1;
        int technicianStart = complaintStart + complaints.length;
        int sink = technicianStart + technicians.length;
        int vertices = sink + 1;

        graph = new ArrayList[vertices];

        for (int i = 0; i < vertices; i++) {
            graph[i] = new ArrayList<>();
        }

        // Source connects to every complaint.
        for (int i = 0; i < complaints.length; i++) {
            addEdge(source, complaintStart + i, 1);
        }

        // Connect each complaint to technicians with matching skills.
        for (int i = 0; i < complaints.length; i++) {
            for (int j = 0; j < technicians.length; j++) {
                for (String skill : technicianSkills[j]) {
                    if (requiredSkills[i].equalsIgnoreCase(skill)) {
                        addEdge(complaintStart + i,
                                technicianStart + j, 1);
                        break;
                    }
                }
            }
        }

        // Each technician can handle at most two complaints.
        for (int j = 0; j < technicians.length; j++) {
            addEdge(technicianStart + j, sink, 2);
        }

        int maxFlow = 0;
        int[] parentNode = new int[vertices];
        int[] parentEdge = new int[vertices];

        while (bfs(source, sink, parentNode, parentEdge) == 1) {
            int current = sink;

            while (current != source) {
                int previous = parentNode[current];
                Edge edge = graph[previous].get(parentEdge[current]);

                edge.capacity--;
                graph[current].get(edge.reverse).capacity++;

                current = previous;
            }

            maxFlow++;
        }

        System.out.println("\n--- CO4: Complaint-to-Technician Assignment ---");

        for (int i = 0; i < complaints.length; i++) {
            int complaintNode = complaintStart + i;
            boolean assigned = false;

            for (Edge edge : graph[complaintNode]) {
                if (edge.to >= technicianStart && edge.to < sink
                        && edge.originalCapacity == 1
                        && edge.capacity == 0) {

                    int technicianIndex = edge.to - technicianStart;

                    System.out.println(complaints[i]
                            + " -> Technician: "
                            + technicians[technicianIndex]
                            + " | Required skill: "
                            + requiredSkills[i]);

                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                System.out.println(complaints[i]
                        + " -> No suitable technician available");
            }
        }

        System.out.println("Total complaints assigned: " + maxFlow);
        System.out.println("Maximum Flow: " + maxFlow);
    }
}
