# Super Drive Cloud Storage
This storage app has the following functionalities

1. **Simple File Storage:** Upload/download/removal of files
2. **Note Management:** Add/update/removal of text notes
3. **Password Management:** Save, edit, and deletiion of website credentials.  


## Requirements and Roadmap
The layers of the application implemented are:

1. The back-end with Spring Boot
2. The front-end with Thymeleaf
3. Application tests with Selenium

### The Back-End

1. Managing user access with Spring Security
 - Unauthorized users are restricted from accessing pages other than the login and signup pages.   

2. Handling front-end calls with controllers
 - Controllers are written for the application that bind application data and functionality to the front-end. 

3. Making calls to the database with MyBatis mappers
 - MyBatis mapper interfaces are implemented to connect the models to the database. 


### The Front-End
The HTML template includes fields, modal forms, success and error message elements, as well as styling and functional components using Bootstrap as a framework.
1. Login page
 - Access to this page is availabe to all, and users can use this page to login to the application. 
 - Shows login errors, like invalid username/password. 

2. Sign Up page
 -Access to this page is availabe to all, and potential users can use this page to sign up for a new account. 
 - Validates that the username supplied does not already exist in the application, and show such signup errors on the page when they arise.

3. Home page
The home page hosts the three required pieces of functionality. The existing template presents them as three tabs that can be clicked through by the user:


 i. Files
  - The user is be able to upload files and see any files they previously uploaded. 
  - The user is able to view/download or delete previously-uploaded files.
 
 ii. Notes
  - The user is able to create notes and see a list of the notes they have previously created.
  - The user is able to edit or delete previously-created notes.

 iii. Credentials
 - The user is able to store credentials for specific websites and see a list of the credentials they've previously stored
 - The user is able to view/edit or delete individual credentials. When the user views the credential, they should be able to see the unencrypted password.



### Testing
Selenium tests are written to verify user-facing functionality:

1. Tests are written for user signup, login, and unauthorized access restrictions.

2. Tests are written for note creation, viewing, editing, and deletion.

3. Tests are written for credential creation, viewing, editing, and deletion.

