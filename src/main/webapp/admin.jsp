<%@ page import="java.util.List" %>
<%@ page import="web.User" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Page</title>
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
            width: 90%;
            margin-top: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: center;
        }

        th {
            background-color: #17a2b8;
            color: #ffffff;
        }

        a.btn {
            margin-top: 20px;
            display: inline-block;
            text-decoration: none;
            background-color: #17a2b8;
            color: #ffffff;
            padding: 10px 20px;
            border-radius: 5px;
        }

        a.btn:hover {
            background-color: #127179;
        }

        .navbar-toggler-icon {
            color: #17a2b8;
        }
        #logout-link {
            position: absolute;
            top: 10px;
            right: 10px;
            color: #ffffff;
            text-decoration: none;
        }
    </style>
</head>
<body class="container-fluid">
    <nav class="navbar navbar-dark bg-dark fixed-top navbar-expand-lg">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggleExternalContent"
                aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="navbar-brand">Admin Page</div>
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
        <a href="logout" id="logout-link">Logout</a>
    </nav>

    <div class="container">
        
        <table class="table table-hover table-striped">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Mobile No</th>
                <th>Deposit Date</th>
                <th>Subject</th>
                <th>Gender</th>
                <th>Document</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            
            <% List<User> userList = (List<User>)request.getAttribute("userList");
            if (userList != null) {
                for (User user : userList) { %>
                    <tr>
                        <td><%= user.getId() %></td>
                        <td><%= user.getName() %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getMobile() %></td>
                        <td><%= user.getDob() %></td>
                        <td><%= user.getCity() %></td>
                        <td><%= user.getGender() %></td>
                        <td><%= user.getDocumentPreview() %></td>
                        <td><a href='editurl?id=<%= user.getId() %>'>Edit</a></td>
                        <td><a href='deleteurl?id=<%= user.getId() %>'>Delete</a></td>
                    </tr>
                <% }
            } else {
                out.println("<tr><td colspan='10'>No user data available.</td></tr>");
            }
            %>
        </table>

    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
