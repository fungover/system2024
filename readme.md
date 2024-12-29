**Build Frontend as part of Maven lifecycle**

**Run Maven Command:**
Use the following Maven command to install Node.js, npm, and the frontend dependencies:

   `mvn clean compile`

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

## Swagger UI Documentation
- Swagger UI can be accessed at: http://localhost:8080/swagger-ui.html.
