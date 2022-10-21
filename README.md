"# WGU-C195-Software-2" 
"# WGU-C195-Software-2" 

WGU C195 Software 2 PA - JavaFX Appointment Scheduler. 
---

Purpose: The purpose of this JavaFX application is to add/modify/delete appointments and customers while connected to a database. In this application, appointments are associated with customers and deleting a customer will delete all appointment information associated with that customer. Appointment information is stored as 	      UTC in the database. When an appointment is pulled, the UTC timezone is translated to the users machine time whatever that may be and displayed in the                 TableView. The TableView information is then compared to the companys theoretical business hours, which are in EST. Translations are done everywhere to ensure 	    appointment date/time robustness. 

---

Author: Dylan Franklin

Contact Information: dfra145@wgu.edu / epicishness100@gmail.com

Student Application Version: V2.0

Date: 09/16/2022 (MM/DD/YYYY)

---

IDE: Intellij Community 2020.01

JDK: Java SE 17.0.1

JavaFX: JavaFX-SDK-17.0.1

---

How to run the program: 
	
	1. Run "main", when you arrive at the login form, use one of the two accepted usernames/passwords.
		1a. USERNAME: 'admin'      PASSWORD: 'admin'
		
		1b. USERNAME: 'test'	 PASSWORD: 'test'
	2. Once logged in, choose which form you wish to visit. (Customer? Appointments? Reports?)
	3. Regardless of what is picked, you'll be able to interact with the program and its data via addition, modification, and deletion of appointments/customers as well as the usage of the search functions.
	4. When finished using the program, logout by going back to the Main Menu form and click log out.
		4a. Alternatively, you can also just close the application to log out.

---

Report: The additional report is found in the reports form under 3a. This report sorts the tableView appointments by Customer, giving them a look at only that selected Customers appointments.

---

MySQL Connector: mysql-connector-java-8.0.25

