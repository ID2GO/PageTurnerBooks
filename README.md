# PageTurnerBooks

the Google Udacity Android Basics Nanodegree study program

Project Specification for:

Inventory App, Stage 1

Layout
Criteria 	Meets Specifications

Overall Layout
	

No UI is required for this project.

Hint: At minimum, you will need a main activity that has methods to read data, a Contract Java class, and a DbHelper Java class.

Note: Even though UI is not required for this Stage, we highly recommend that you test your insert/read methods with log calls. Often, students do not realize their code has SQL syntax errors until the app is run and the methods are called which results in the project not passing.

Functionality
Criteria 	Meets Specifications

Compile Time Errors
	

The code compiles without errors.

Table Definition
	

There exists a contract class that defines name of table and constants.

Inside the contract class, there is an inner class for each table created.

The contract contains at minimum constants for the Product Name, Price, Quantity, Supplier Name, and Supplier Phone Number.

Table Creation
	

There exists a subclass of SQLiteOpenHelper that overrides onCreate() and onUpgrade().

Data Insertion
	

There is a single insert method that adds:

    Product Name
    Price
    Quantity
    Supplier Name
    Supplier Phone Number

It is up to you to decide what datatype (e.g. INTEGER, STRING) each of these values should be; however, it is required that there are at least 2 different datatypes (e.g. INTEGER, STRING).

Data Reading
	

There is a single method that uses a Cursor from the database to perform a query on the table to retrieve at least one column of data. Also the method should close the Cursor after it's done reading from it.

Code Readability
Criteria 	Meets Specifications

Readability
	

Code is easily readable such that a fellow programmer can understand the purpose of the app.

Naming Conventions
	

All variables, methods, and resource IDs are descriptively named such that another developer reading the code can easily understand their function.

Format
	

The code is properly formatted:

    No unnecessary blank lines
    No unused variables or methods
    No commented out code

The code also has proper indentation when defining variables and methods.
