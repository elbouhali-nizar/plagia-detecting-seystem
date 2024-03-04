package web;

import java.sql.*;
import java.util.*;

public class PlagiarismDetectorDoc {
	

    public static Map<String, String> fetchUserDocumentsFromDatabase(String subjectPath) {
        Map<String, String> userDocuments = new HashMap<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
                 PreparedStatement ps = con.prepareStatement("SELECT name, document FROM user WHERE city = ?");
            ) {
            	System.out.println("Subject Path: " + subjectPath);

                ps.setString(1, subjectPath);

                // Debugging output
                System.out.println("Executing SQL Query: " + ps.toString());

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String username = rs.getString("name");
                        String userDocument = rs.getString("document");
                        userDocuments.put(username, userDocument);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return userDocuments;
    }

    public static double calculateJaccardSimilarity(String text1, String text2) {
        Set<String> set1 = tokenize(text1);
        Set<String> set2 = tokenize(text2);

        // Debugging output
        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        // Debugging output
        System.out.println("Intersection: " + intersection);
        System.out.println("Union: " + union);

        double similarity = (double) intersection.size() / union.size();

        // Debugging output
        System.out.println("Similarity: " + similarity);

        return similarity;
    }

    private static Set<String> tokenize(String text) {
        String[] words = text.toLowerCase().split("\\W+");
        return new LinkedHashSet<>(Arrays.asList(words));
    }
}
