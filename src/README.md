import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class PatternSearch {
   public PatternSearch() {
   }

   static boolean search(String var0, String var1) {
      var0 = var0.toLowerCase();
      var1 = var1.toLowerCase();

      for(int var2 = 0; var2 <= var0.length() - var1.length(); ++var2) {
         int var3;
         for(var3 = 0; var3 < var1.length() && var0.charAt(var2 + var3) == var1.charAt(var3); ++var3) {
         }

         if (var3 == var1.length()) {
            return true;
         }
      }

      return false;
   }

   public static void main(String[] var0) {
      Scanner var1 = new Scanner(System.in);
      System.out.print("Enter pattern to search: ");
      String var2 = var1.nextLine();

      try {
         BufferedReader var3 = new BufferedReader(new FileReader("complaints.txt"));
         boolean var5 = false;

         String var4;
         while((var4 = var3.readLine()) != null) {
            String[] var6 = var4.split("\\|");
            String var7 = var6[3];
            if (search(var7, var2)) {
               System.out.println("\nComplaint Found:");
               System.out.println("ID: " + var6[0]);
               System.out.println("Customer: " + var6[1]);
               System.out.println("Complaint: " + var6[3]);
               System.out.println("Priority: " + var6[4]);
               System.out.println("Status: " + var6[5]);
               var5 = true;
            }
         }

         var3.close();
         if (!var5) {
            System.out.println("No matching complaint found.");
         }
      } catch (Exception var8) {
         System.out.println("Error reading complaints.txt");
      }

      var1.close();
   }
}
