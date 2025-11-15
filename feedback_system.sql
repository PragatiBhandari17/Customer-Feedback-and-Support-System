CREATE DATABASE FeedbackSystem;
USE FeedbackSystem;

CREATE TABLE Customers (
    CustomerID INT PRIMARY KEY IDENTITY(1,1),
    Name VARCHAR(100),
    Email VARCHAR(100) UNIQUE,
    Password VARCHAR(100),
    Phone VARCHAR(10)
);

CREATE TABLE Products (
    ProductID INT PRIMARY KEY IDENTITY(1,1),
    Name VARCHAR(100),
    Description VARCHAR(MAX),
    Category VARCHAR(100)
);

CREATE TABLE FeedbackCategories (
    CategoryID INT PRIMARY KEY IDENTITY(1,1),
    Name VARCHAR(100)
);

CREATE TABLE Feedbacks (
    FeedbackID INT PRIMARY KEY IDENTITY(1,1),
    CustomerID INT,
    Message VARCHAR(MAX),
    Rating INT CHECK (Rating BETWEEN 1 AND 5),
    ProductID INT,
    CategoryID INT,
    CreatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (CategoryID) REFERENCES FeedbackCategories(CategoryID)
);

CREATE TABLE SupportTickets (
    TicketID INT PRIMARY KEY IDENTITY(1,1),
    CustomerID INT,
    Subject VARCHAR(200),
    Description VARCHAR(MAX),
    Status VARCHAR(50) DEFAULT 'Open',
    CreatedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE TicketCategories (
    CategoryID INT PRIMARY KEY IDENTITY(1,1),
    Name VARCHAR(100)
);

CREATE TABLE Staff (
    StaffID INT PRIMARY KEY IDENTITY(1,1),
    Name VARCHAR(100),
    Email VARCHAR(100) UNIQUE,
    Password VARCHAR(100),
    Role VARCHAR(50)
);

CREATE TABLE TicketAssignments (
    AssignmentID INT PRIMARY KEY IDENTITY(1,1),
    TicketID INT,
    StaffID INT,
    AssignedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (TicketID) REFERENCES SupportTickets(TicketID),
    FOREIGN KEY (StaffID) REFERENCES Staff(StaffID)
);

CREATE TABLE TicketResponses (
    ResponseID INT PRIMARY KEY IDENTITY(1,1),
    TicketID INT,
    StaffID INT,
    ResponseText VARCHAR(MAX),
    RespondedAt DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (TicketID) REFERENCES SupportTickets(TicketID),
    FOREIGN KEY (StaffID) REFERENCES Staff(StaffID)
);

CREATE TABLE ActivityLogs (
    LogID INT PRIMARY KEY IDENTITY(1,1),
    Activity VARCHAR(200),
    PerformedBy VARCHAR(100),
    Timestamp DATETIME DEFAULT GETDATE()
);

INSERT INTO Customers VALUES
('Ravi Kumar', 'ravi@example.com', 'ravi123', '9876543210'),
('Aarti Sharma', 'aarti@example.com', 'aarti123', '9876543211'),
('Vikas Gupta', 'vikas@example.com', 'vikas123', '9876543212'),
('Meena Joshi', 'meena@example.com', 'meena123', '9876543213'),
('Anil Yadav', 'anil@example.com', 'anil123', '9876543214');

INSERT INTO Products (Name, Description, Category) VALUES
('Laptop', 'Intel i5, 8GB RAM, SSD', 'Electronics'),
('Smartphone', 'Android, 6GB RAM', 'Electronics'),
('Refrigerator', 'Double Door, 240L', 'Appliances'),
('Washing Machine', '7kg Front Load', 'Appliances'),
('Headphones', 'Bluetooth, Noise Cancelling', 'Accessories');

INSERT INTO FeedbackCategories (Name) VALUES
('Performance'),
('Usability'),
('Pricing'),
('Support'),
('Durability');

INSERT INTO Feedbacks (CustomerID, Message, Rating, ProductID, CategoryID) VALUES
(1, 'Great performance and battery life.', 5, 1, 1),
(2, 'User-friendly interface.', 4, 2, 2),
(3, 'Could be cheaper.', 3, 5, 3),
(4, 'Very helpful support.', 5, 4, 4),
(5, 'Sturdy and durable.', 4, 3, 5);

INSERT INTO TicketCategories (Name) VALUES
('Login Issue'),
('Payment Failed'),
('Product Defective'),
('Late Delivery'),
('Return/Exchange');

INSERT INTO SupportTickets (CustomerID, Subject, Description, Status) VALUES
(1, 'Login Error', 'Unable to login since yesterday.', 'Open'),
(2, 'Payment issue', 'Amount deducted but order not confirmed.', 'Open'),
(3, 'Damaged item received', 'Refrigerator has a dent.', 'Open'),
(4, 'Late delivery', 'Expected delivery was 3 days ago.', 'Open'),
(5, 'Wrong product delivered', 'Ordered headphones but received mouse.', 'Open');

INSERT INTO Staff (Name, Email, Password, Role) VALUES
('Kunal Singh', 'kunal@support.com', 'kunal123', 'Support'),
('Neha Mehta', 'neha@support.com', 'neha123', 'Support'),
('Raj Patel', 'raj@support.com', 'raj123', 'Support'),
('Simran Kaur', 'simran@support.com', 'simran123', 'Support'),
('Admin', 'admin@support.com', 'admin123', 'Admin');

INSERT INTO TicketAssignments (TicketID, StaffID) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 1);

INSERT INTO TicketResponses (TicketID, StaffID, ResponseText) VALUES
(1, 1, 'Issue acknowledged. Reset link sent.'),
(2, 2, 'We are checking with the payment gateway.'),
(3, 3, 'We apologize, replacement is arranged.'),
(4, 4, 'Delivery partner contacted. ETA updated.'),
(5, 1, 'Please return the item for exchange.');

INSERT INTO ActivityLogs (Activity, PerformedBy) VALUES
('Customer registered', 'System'),
('Feedback submitted', 'ravi@example.com'),
('Ticket raised', 'aarti@example.com'),
('Ticket assigned', 'kunal@support.com'),
('Ticket resolved', 'kunal@support.com');