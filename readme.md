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
### 1. Get client ID and client secret
### 2. Add client ID and client secret to your application:

***IntelliJ navbar -> Run -> Edit configurations -> Environment variables***
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