# ⭐ **CUSTOMER FEEDBACK & SUPPORT SYSTEM**

The **Customer Feedback & Support System** is a Java-based desktop application designed to allow customers to submit feedback and support tickets, while staff members can manage, respond to, and resolve these tickets.  
The system uses **Java Swing** for the frontend and **MS SQL Server** for the backend database.


# 📌 Features
### 👤 **Customer Features**
- Register and log into the system  
- Submit product feedback with rating  
- Create support tickets  
- View ticket status  

### 🧑‍💼 **Staff Features**
- Staff login  
- View assigned support tickets  
- Respond to tickets  
- Update ticket status (Open / In-Progress / Resolved)  
- View activity logs  

### 🗄 **Admin Features**
- Manage staff  
- View all tickets  
- Monitor feedback categories  
- Track system activity  


# 📂 Project Structure
project-folder/
│
├── DBConnection.java            # Database connection class
├── LoginForm.java               # Customer login UI
├── FeedbackForm.java            # Submit feedback UI
├── SupportTicketForm.java       # Raise support ticket UI
├── StaffLogin.java              # Staff login UI
├── StaffDashboard.java          # Staff dashboard UI
│
├── feedback_system.sql          # Complete SQL database schema + sample data
├── mssql-jdbc-12.10.0.jre11.jar # SQL Server JDBC driver
│
└── README.md                    # Documentation


# 🗄 Database Schema (MS SQL Server)
The full database schema is located in: feedback_system.sql

It includes the following tables:  
- Customers  
- Products  
- FeedbackCategories  
- Feedbacks  
- SupportTickets  
- TicketCategories  
- Staff  
- TicketAssignments  
- TicketResponses  
- ActivityLogs  
The SQL file also contains **sample data** for testing the system.  


# 🔌 Database Connection
The project uses a **JDBC connection** to MS SQL Server.
Connection information is stored in: DBConnection.java
Make sure to update:
- Database name  
- Username  
- Password  
- SQL Server connection URL  
You must include the JDBC driver:
mssql-jdbc-12.10.0.jre11.jar


# 👇 How the System Works
### 1️⃣ **Customer Workflow**
1. Customer logs in  
2. Submits feedback on a product  
3. Creates support tickets  
4. Can track status (Open → Assigned → Resolved)

### 2️⃣ **Staff Workflow**
1. Staff logs in using StaffLogin  
2. Dashboard shows assigned tickets  
3. Staff responds and updates ticket status  
4. Activity logs are generated automatically  


# ▶️ How to Run the Project
### ✔ Requirements
- Java JDK 11+  
- Any Java IDE (NetBeans, IntelliJ, Eclipse)  
- MS SQL Server  
- JDBC SQL Server Driver (.jar included)

### ✔ Steps
1. Import the Java project into your IDE  
2. Run the SQL file: feedback_system.sql

to create the database and sample data  
3. Update database credentials in DBConnection.java  
4. Add JDBC Driver .jar to project libraries  
5. Run the application  
6. Use sample logins (stored in SQL)  


# 🧪 Sample Logins
### 👤 **Customer Example**
Email: [ravi@example.com](mailto:ravi@example.com)
Password: ravi123

### 🧑‍💼 **Staff Example**
Email: [kunal@support.com](mailto:kunal@support.com)
Password: kunal123


# 📦 Technologies Used
- **Java Swing** (Frontend UI)
- **MS SQL Server** (Database)
- **JDBC** (Database Connectivity)
- **Object-Oriented Programming**
- **SQL Constraints, Foreign Keys, Activity Logs**


# 📈 Future Enhancements
- Add email notifications for ticket updates  
- Add analytics dashboard for admins  
- Convert system into a web application  
- Add JWT-based authentication  
- Implement live chat support  
