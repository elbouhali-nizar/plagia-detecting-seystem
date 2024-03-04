package web;

import java.sql.*;
import java.util.*;

public class PlagiarismDetector {

    public static Map<String, Map.Entry<String, String>> fetchUserDocumentsWithSubjectsFromDatabase() {
        Map<String, Map.Entry<String, String>> userDocumentsWithSubjects = new HashMap<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection("jdbc:mysql:///usermgmt", "root", "root");
                 PreparedStatement ps = con.prepareStatement("SELECT name, document, city FROM user");
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    String username = rs.getString("name");
                    String userDocument = rs.getString("document");
                    String userSubject = rs.getString("city");
                    userDocumentsWithSubjects.put(username, new AbstractMap.SimpleEntry<>(userDocument, userSubject));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return userDocumentsWithSubjects;
    }

    public static Map<String, Map<String, Double>> detectPlagiarismBetweenUsersForSubject(String subject) {
        Map<String, Map<String, Double>> plagiarismResults = new HashMap<>();
        Map<String, Map.Entry<String, String>> userDocumentsWithSubjects = fetchUserDocumentsWithSubjectsFromDatabase();

        for (String user1 : userDocumentsWithSubjects.keySet()) {
            String subject1 = userDocumentsWithSubjects.get(user1).getValue();

            if (subject1.equals(subject)) {
                for (String user2 : userDocumentsWithSubjects.keySet()) {
                    String subject2 = userDocumentsWithSubjects.get(user2).getValue();

                    if (!user1.equals(user2) && subject1.equals(subject2)) {
                        String document1 = userDocumentsWithSubjects.get(user1).getKey();
                        String document2 = userDocumentsWithSubjects.get(user2).getKey();

                        // Ensure that only unique pairs are added
                        String pair1 = user1 + "-" + user2;
                        String pair2 = user2 + "-" + user1;

                        if (!plagiarismResults.containsKey(pair1) && !plagiarismResults.containsKey(pair2)) {
                            double similarity = calculateJaccardSimilarity(document1, document2);

                            plagiarismResults
                                    .computeIfAbsent(pair1, k -> new HashMap<>())
                                    .put(pair2, similarity);
                        }
                    }
                }
            }
        }

        return plagiarismResults;
    }

    public static double calculateJaccardSimilarity(String text1, String text2) {
        Set<String> set1 = tokenize(text1);
        Set<String> set2 = tokenize(text2);

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    private static Set<String> tokenize(String text) {
        String[] words = text.toLowerCase().split("\\W+");
        return new LinkedHashSet<>(Arrays.asList(words));
    }
}
