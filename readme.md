BE/FE strucutre to increase seperation.
FE is React using Vite install.
-npm run dev => runs React only,
-npm run start => runs SB + React.
###
TODO: Proxy is "configured" in FE\package.json, this is to help with communication BE => FE

###
![image](https://github.com/user-attachments/assets/53d1807f-fdeb-4541-87a8-eb7089aa216a)

---

## GitHub OAuth2
### 1. Get client ID and client secret (Use this [link](https://github.com/settings/developers) to create an OAuth app to use locally)
1. Set the Homepage URL to http://localhost:8080
2. Set the Authorization callback URL to http://localhost:8080/login/oauth2/code/github
3. Register the application
4. Generate client secret
### 2. Add client ID and client secret to your application:

***Option 1:***  
IntelliJ navbar -> Run -> Edit configurations -> Environment variables

***Option 2:***  
Create a .env file in the root of the project
### Add:
```
GITHUB_CLIENT_ID=id (replace id with your client ID)
GITHUB_CLIENT_SECRET=secret (replace secret with your client secret)
```

### 3. Run the application

### Endpoints:
```
http://localhost:8080/
http://localhost:8080/login
http://localhost:8080/logout
```
---  
### Switch between profiles in ***application.properties***:
```
spring.profiles.active=production  
spring.profiles.active=development
```

## Steps to Generate an App Password
### 1. Log in to your Google Account: 
### 2. Navigate to the Security Page: 
1. After logging in, click on "Security" in the left navigation panel. 
### 3. Enable Two-Factor Authentication: 
1. Find the "2-Step Verification" section and click "Get Started." 
   Follow the on-screen instructions to enable two-factor authentication. 
   If you have already enabled it, skip this step. 
### 4. Generate an App Password:
1. Return to the "Security" page and find the "App passwords" section. 
2. If you cannot find the "App passwords" section, type "App passwords" into the search bar. 
3. Click on the "App passwords" link. You may be asked to log in again. 
4. From the "Select app" dropdown menu, choose "Mail," and from 
   the "Select device" dropdown menu, choose "Other (Custom name)." 
   Enter a name (e.g., Spring Boot App) and click "Generate." 
5. A 16-character app password will be displayed. Copy this 
   password and set it in the .env file as MAIL_PASSWORD. 

#### Configuration for Gmail Users
Add them to .env file in the root of the project 
```
MAIL_HOST=smtp.gmail.com 
MAIL_PORT=587 
MAIL_USERNAME= (replace your gmail adress) 
MAIL_PASSWORD=(replace with your generated app password) 
NOTIFICATION_EMAIL_RECIPIENT=(replace your gmail adress)
```