# Canteen Management System 🍽️

A Java Swing-based desktop application designed to streamline food ordering and inventory tracking for a canteen. The system includes admin authentication, dynamic menu categorization, order processing with tax calculation, and real-time inventory management. It uses an embedded H2 database and JDBC for seamless, zero-setup data storage.

## 🚀 Features

* **Admin Login:** Secure authentication system for canteen staff.
* **Dynamic Menu & Ordering:** Browse categorized menu items (Sandwiches, Burgers, etc.) and place orders using an interactive GUI.
* **Real-Time Inventory Tracking:** Stock levels are automatically checked and decremented upon successful orders using SQL locking.
* **Dynamic Billing:** Automatically calculates subtotals, applies taxes, and generates itemized order receipts.
* **Zero-Setup Database:** Auto-initializes tables and seeds default menu items on the first run.

## 💻 Tech Stack

* **Language:** Java (Core)
* **GUI Framework:** Java Swing, AWT, ActionListener
* **Database:** Embedded H2 Database Engine
* **API:** JDBC (Java Database Connectivity)

## 📋 Prerequisites

To run this project locally, ensure you have the following installed on your system:
* Java Development Kit (JDK) 8 or higher
* A Java IDE (like Visual Studio Code, Eclipse, or IntelliJ)
* The **H2 Database JDBC Driver** (`h2-*.jar`) added to your project's build path/classpath.

## 🛠️ Database Setup

Unlike traditional setups, this project uses an **Embedded H2 Database**.
1. You do **not** need to install MySQL, Workbench, or create tables manually.
2. Simply run the application! The system will automatically create a local `canteen_db` file in your project directory.
3. It will automatically generate the required `users`, `inventory`, and `transactions` tables.
4. Use the default auto-generated credentials to log in: **Username:** `admin` | **Password:** `admin123`.

   
