"# WGU-C195-Software-2" 
"# WGU-C195-Software-2" 

# WGU C195 Software 2 PA - JavaFX Appointment Scheduler. #

---
## SCREENSHOTS ##

![JavaAppointmentSchedulerLoginScreenshot](https://github.com/Xenonous/Java-Appointment-Scheduler/assets/112530437/157251a3-fe43-42ea-a5f7-a9b268fe15fd)

![JavaAppointmentSchedulerCustomersScreenshot](https://github.com/Xenonous/Java-Appointment-Scheduler/assets/112530437/d34e73e3-42bf-466d-9b26-ec3d022da40f)

![JavaAppointmentSchedulerAppointmentsScreenshot](https://github.com/Xenonous/Java-Appointment-Scheduler/assets/112530437/a92c7c27-e4d0-49fa-b7e5-fa194cd9bbc8)

![JavaAppointmentSchedulerReportsScreenshot](https://github.com/Xenonous/Java-Appointment-Scheduler/assets/112530437/45194e23-9226-4747-9d2c-7801f3753190)

![JavaAppointmentSchedulerAddAppointmentsScreenshot](https://github.com/Xenonous/Java-Appointment-Scheduler/assets/112530437/b4725336-871e-4e1c-89f0-153754ef27e0)

---
## FUNCTIONALITIES/DEMONSTRATED SKILLS ##

* Used Java Database Connectivity (JDBC) API to manipulate an external SQL Database.
* Security through a user login form, using the Database to manage valid user accounts.
  * Login attempts and time are recorded in a .txt log file.
* ADD, SELECT, MODIFY, and DELETE items from a MySQL database that takes into consideration Foreign Key constraints in the database.
* Automatic notification generation if there's an appointment within 15min after logging in. 
* Robust Time Zone support. Automatic timezone conversion done at all levels based on the local machines' time zone. LocalDateTime, Timestamp, and ZonedDateTime classes used.
* Logic checks throughout the entire application to ensure proper entry of data.
* Automatic filling of TextField and ComboBox fields when MODIFYING data.
* General User Interface support using JavaFX.
* Github and Git for Version Control
* Lambda Expressions
  

---
## PURPOSE ##

   The purpose of this JavaFX application is to add/modify/delete appointments and customers while connected to a database. In this application, appointments are associated with customers and deleting a customer will delete all appointment information associated with that customer. Appointment information is stored as 	      UTC in the database. When an appointment is pulled, the UTC timezone is translated to the users machine time, whatever that may be, and displayed in the                TableView. The TableView information is then compared to the companys theoretical business hours, which is in EST. Translations are done everywhere to ensure 	    appointment date/time robustness. 

---
## DEVELOPMENT ENVIONMENT ##

   IDE: Intellij Community 2020.01

   JDK: Java SE 17.0.1

   JavaFX: JavaFX-SDK-17.0.1
   
---
## GENERAL INFORMATION ##

Author: Dylan Franklin

Contact Information: ~~dfra145@wgu.edu~~ / epicishness100@gmail.com / dfra0605@gmail.com

Student Application Version: V2.0

Date: 09/16/2022 (MM/DD/YYYY)

---
## USER MANUAL ##
	
1. Run "main", when you arrive at the login form, use one of the two accepted usernames/passwords.
   
	1a. USERNAME: 'admin'      PASSWORD: 'admin'
		
	1b. USERNAME: 'test'	 PASSWORD: 'test'
3. Once logged in, choose which form you wish to visit. (Customer? Appointments? Reports?)
4. Regardless of what is picked, you'll be able to interact with the program and its data via addition, modification, and deletion of appointments/customers as well as the usage of the search functions.
5. When finished using the program, logout by going back to the Main Menu form and click log out.
	4a. Alternatively, you can also just close the application to log out.

---

Report: The additional report is found in the reports form under 3a. This report sorts the tableView appointments by Customer, giving them a look at only that selected Customers appointments.

---

MySQL Connector: mysql-connector-java-8.0.25

