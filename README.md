# Milk Sales Management Software 

## Overview
This Milk Sales Management Software is a Java Swing application designed to facilitate the management of milk sales in a retail environment. It features two user levels: Admin and Cashier. The Admin has privileges to manage user accounts, record milk sales, and analyze sales data. On the other hand, Cashiers can create milk sales, access their own sales records, and view sales charts.

## Features
### User Management:
- Admin can create, update, and delete user accounts.
- Users are categorized into Admin and Cashier roles.

### Milk Sales Recording:
- Cashiers can create milk sales records specifying the quantity, type, and price.
- Sales records are stored in a MySQL database for easy retrieval and management.

### Sales Analysis:
- Admin can analyze sales data including total sales, top-selling products, and trends over time.
- Charts and reports are generated to visualize sales data for better insights.

## User Levels
### Admin:
- Can manage user accounts.
- Can record milk sales.
- Can analyze sales data.

### Cashier:
- Can create milk sales.
- Can access own sales records.
- Can view sales charts related to their sales.

## Technologies Used
- Java Swing: For the graphical user interface.
- MySQL: For storing and managing data.
- JDBC: For database connectivity.

## Installation
1. **Clone the Repository:**
    ```bash
    git clone https://github.com/derroh/Milk-Sales-Management-Software.git
    ```

2. **Set up MySQL Database:**
   - Create a MySQL database and configure the connection details in the application.
   - Execute the SQL scripts provided in the database directory to set up the necessary tables and data.

3. **Compile and Run the Application:**
   - Compile the Java files using a Java compiler.
   - Run the application by executing the main Java class.

## Usage
### Login:
Upon launching the application, users will be prompted to login with their credentials.

### Admin Actions:
- Admin can manage user accounts from the admin panel.
- Admin can record milk sales and analyze sales data from the respective panels.

### Cashier Actions:
- Cashiers can create milk sales from the cashier panel.
- Cashiers can access their own sales records and view sales charts for their sales.

## Contributing
Contributions to improve this Milk Sales Management Software are welcome! Please feel free to fork this repository, make your changes, and submit a pull request.

## License
This Milk Sales Management Software is open-source software licensed under the MIT license.






