<%@ page import="web.PlagiarismDetectorDoc" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map.Entry" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.nio.file.Paths" %>

<%
    // Subject paths map (replace with your actual paths)
    Map<String, String> subjectPaths = new HashMap<>();
    subjectPaths.put("Maths", "C://Users/Home/Documents/Maths.txt");
    subjectPaths.put("JEE", "C://Users/Home/Documents/JEE.txt");
    subjectPaths.put("Python", "C://Users/Home/Documents/Python.txt");
    subjectPaths.put("ML", "C://Users/Home/Documents/ML.txt");

    // Get selected subject from the request parameter
    String selectedSubject = request.getParameter("selectedSubject");

    // Default subject if none selected
    if (selectedSubject == null || !subjectPaths.containsKey(selectedSubject)) {
        selectedSubject = "Maths";
    }

    // Fetch user documents from the database using the selected subject path
    Map<String, String> userDocuments = PlagiarismDetectorDoc.fetchUserDocumentsFromDatabase(selectedSubject);
	System.out.println("user:"+userDocuments);
    // Read the content of the text document
    String sampleDocument = new String(Files.readAllBytes(Paths.get(subjectPaths.get(selectedSubject))), "UTF-8");

    // Debugging output
    

    // Calculate Jaccard Similarity for each user document
    Map<String, Double> results = new HashMap<>();
    for (Map.Entry<String, String> entry : userDocuments.entrySet()) {
        double similarity = PlagiarismDetectorDoc.calculateJaccardSimilarity(entry.getValue(), sampleDocument);
        results.put(entry.getKey(), similarity);
    }

    // Debugging output
    
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plagiarism Detection Results</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #343a40;
            color: #ffffff;
            padding-top: 80px;
        }

        h2 {
            color: #17a2b8;
            margin-bottom: 20px;
        }

        .offcanvas-collapse {
            position: fixed;
            top: 56px; /* Adjust the top based on your page header height */
            bottom: 0;
            overflow-y: auto;
            background-color: #343a40;
            transition: transform 0.3s ease-in-out;
            z-index: 1;
        }

        .navbar-toggler-icon {
            color: #17a2b8;
        }

        .navbar-toggler {
            border-color: #17a2b8;
            outline: none;
        }

        .navbar-collapse {
            border-color: #17a2b8;
        }

        .navbar-dark .navbar-toggler-icon {
            filter: brightness(0) invert(1);
        }

        .bg-primary {
            background-color: #17a2b8 !important;
        }

        .navbar-brand {
            color: #ffffff;
            font-weight: bold;
        }

        .navbar-nav a {
            color: #ffffff;
        }

        .navbar-nav a:hover {
            color: #17a2b8;
        }

        .navbar-toggler:hover {
            background-color: #17a2b8;
        }

        table {
            margin: auto;
            margin-top: 50px;
            border-collapse: collapse;
            width: 80%;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: center;
            border: 1px solid #dee2e6;
        }

        th {
            background-color: #17a2b8;
            color: #ffffff;
        }
         label[for="subjectSelect"] {
        display: block;
        font-size: 16px;
        font-weight: bold;
        margin-top: 20px;
        color: #17a2b8; /* Match your existing color scheme */
    }

    select#subjectSelect {
        width: 80px;
        padding: 5px ;
        border: 1px solid #17a2b8;
        border-radius: 5px;
        box-sizing: border-box;
        font-size: 16px;
        color: #343a40; /* Match your existing color scheme */
    }
    </style>
</head>
<body class="container-fluid">
    <nav class="navbar navbar-dark bg-dark fixed-top navbar-expand-lg">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggleExternalContent"
                aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="navbar-brand">Plagiarism Detection Results</div>
        <div class="collapse navbar-collapse" id="navbarToggleExternalContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="admin">All Users</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="PlagiaDoc.jsp">Plagia&Doc</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="PlagiaUsers.jsp">Plagia&Users</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container">
        <h2 class="text-center mt-5">Plagiarism Detection Results</h2>

        <!-- Dropdown to select subject -->
        <form action="PlagiaDoc.jsp" method="get" class="mb-3">
            <div class="form-group">
                <label for="subjectSelect" class="text-white">Select Subject:</label>
                <select class="form-control" id="subjectSelect" name="selectedSubject">
                    <% 
                        for (String subject : subjectPaths.keySet()) {
                            // Select the current subject in the dropdown
                            String selectedAttr = subject.equals(selectedSubject) ? "selected" : "";
                    %>
                    <option value="<%= subject %>" <%= selectedAttr %>><%= subject %></option>
                    <%
                        }
                    %>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>

       <!-- Display results in the table -->
<table class="table table-bordered table-striped">
    <thead>
        <tr>
            <th>Username</th>
            <th>Jaccard Similarity (%)</th>
        </tr>
    </thead>
    <tbody>
        <% 
            // Iterate over the results and display them in the table
            Iterator<Entry<String, Double>> iterator = results.entrySet().iterator();
            System.out.println("Selected Subject: " + selectedSubject);

            while (iterator.hasNext()) {
                Entry<String, Double> entry = iterator.next();
        %>
        <tr>
            <td><%= entry.getKey() %></td>
            <td><%= entry.getValue() * 100 %></td>
        </tr>
        <%
            }
        %>
    </tbody>
</table>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
