# 📦 Inventory Management System

## 📝 Project Description
This is a simple GUI-based Inventory Management System built using Java Swing and JDBC. It allows users to add, view, update, and delete inventory items with real-time database connectivity. The project is designed for easy inventory tracking and management, especially suited for small businesses and academic use.

---

## 🚀 Features
- ✅ Add new inventory items  
- ✅ View all inventory records in a table  
- ✅ Update existing inventory entries  
- ✅ Delete items from the inventory  
- ✅ Simple and responsive Java Swing GUI  
- ✅ JDBC integration with MySQL  
- ✅ Clean separation using Model, DAO, and GUI layers  

---

## 🛠️ Technologies Used
- Java (Swing for GUI)
- JDBC (Java Database Connectivity)
- MySQL (for database)
- IDE: Visual Studio Code

---

InventoryManagementSystemGUVI/
│
├── .vscode/                        # VS Code configuration
│   ├── launch.json
│   └── settings.json
│
├── bin/                            # Compiled Java bytecode files (.class)
│   ├── dao/
│   │   ├── ProductDAO.class
│   │   ├── SupplierDAO.class
│   │   └── UserDAO.class
│   ├── model/
│   │   ├── Product.class
│   │   ├── Supplier.class
│   │   └── User.class
│   ├── ui/
│   │   ├── InventoryFrame.class
│   │   ├── InventoryFrame$1.class
│   │   ├── LoginFrame.class
│   │   ├── LoginFrame$1.class
│   │   └── ProductFormPanel.class
│   └── utils/
│       ├── DatabaseConnection.class
│       ├── TestDBConnection.class
│       └── TestUserDAO.class
│
├── lib/                            # External libraries
│   └── mysql-connector-j-9.3.0.jar
│
├── src/                            # Source code
│   ├── dao/
│   │   ├── ProductDAO.java
│   │   ├── SupplierDAO.java
│   │   └── UserDAO.java
│   ├── model/
│   │   ├── Product.java
│   │   ├── Supplier.java
│   │   └── User.java
│   ├── ui/
│   │   ├── InventoryFrame.java
│   │   ├── LoginFrame.java
│   │   └── ProductFormPanel.java
│   └── utils/
│       ├── DatabaseConnection.java
│       ├── TestDBConnection.java
│       └── TestUserDAO.java
│
└── README.md                       # Project documentation


