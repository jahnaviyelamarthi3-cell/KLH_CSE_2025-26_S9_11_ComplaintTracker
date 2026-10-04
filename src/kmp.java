import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class ComplaintTrackerKMP {

    // Build LPS (Longest Prefix Suffix) array
    public static int[] buildLPS(String pattern) {
        int[] lps = new int[pattern.length()];

        int length = 0;
        int i = 1;

        while (i < pattern.length()) {

            if (Character.toLowerCase(pattern.charAt(i)) ==
                Character.toLowerCase(pattern.charAt(length))) {

                length++;
                lps[i] = length;
                i++;

            } else {

                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // KMP Pattern Search
    public static boolean KMPSearch(String text, String pattern) {

        if (pattern.length() == 0) {
            return true;
        }

        int[] lps = buildLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {

            if (Character.toLowerCase(text.charAt(i)) ==
                Character.toLowerCase(pattern.charAt(j))) {

                i++;
                j++;

                if (j == pattern.length()) {
                    return true;
                }

            } else {

                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Corpus folder
        String folderPath = "corpus";

        File folder = new File(folderPath);

        // Check corpus folder
        if (!folder.exists() || !folder.isDirectory()) {

            System.out.println("ERROR: Corpus folder not found!");
            System.out.println("Please create a folder named 'corpus'");
            System.out.println("and place your complaint text files inside it.");

            scanner.close();
            return;
        }

        // Get all files
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {

            System.out.println("ERROR: No files found in corpus folder.");

            scanner.close();
            return;
        }

        // Ask for search pattern
        System.out.println("========================================");
        System.out.println("       COMPLAINT TRACKER - KMP");
        System.out.println("========================================");

        System.out.print("Enter complaint keyword to search: ");

        String pattern = scanner.nextLine().trim();

        if (pattern.length() == 0) {

            System.out.println("Please enter a keyword.");

            scanner.close();
            return;
        }

        System.out.println();
        System.out.println("Searching for: " + pattern);
        System.out.println("----------------------------------------");

        int totalCorpusFiles = 0;
        int matchedFiles = 0;

        // Search every corpus file
        for (File file : files) {

            if (file.isFile() &&
                file.getName().toLowerCase().endsWith(".txt")) {

                totalCorpusFiles++;

                try {

                    // Java 8 compatible file reading
                    String text = new String(
                        Files.readAllBytes(file.toPath())
                    );

                    // Apply KMP
                    if (KMPSearch(text, pattern)) {

                        matchedFiles++;

                        System.out.println("MATCH FOUND");
                        System.out.println("File: " + file.getName());
                        System.out.println();

                    }

                } catch (IOException e) {

                    System.out.println(
                        "Error reading file: " + file.getName()
                    );
                }
            }
        }

        // Display final result
        System.out.println("========================================");
        System.out.println("             SEARCH RESULT");
        System.out.println("========================================");

        System.out.println("Pattern searched : " + pattern);
        System.out.println("Corpus files     : " + totalCorpusFiles);
        System.out.println("Matching files   : " + matchedFiles);
        System.out.println("Algorithm        : KMP");
        System.out.println("Time Complexity  : O(n + m)");

        System.out.println("========================================");

        scanner.close();
    }
}
