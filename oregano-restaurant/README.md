# OREGANO Restaurant Ordering System

Java 21, Maven, Swing, JDBC, MySQL

Overview
- Desktop Swing app to view menu (Starters, Main Courses, Drinks) and place orders.
- Orders are saved to a MySQL database using JDBC in a single transaction.

Prerequisites
- Java 21
- Maven
- MySQL server

Setup
1. Create a MySQL database, e.g. `oregano_db`.
2. Update DB credentials in `src/main/java/com/oregano/db/DBConnection.java`.
3. Run SQL to create schema and preload data:
   - `mysql -u your_user -p oregano_db < db/init.sql`

Build
- mvn clean package

Run
- java -jar target/oregano-restaurant-1.0-SNAPSHOT-jar-with-dependencies.jar

Notes
- The jar name above is produced by the Maven assembly plugin.
- The UI uses 5 selection rows where total quantity across all selected items must be between 1 and 5 inclusive.
