BE/FE strucutre to increase seperation.
FE is React using Vite install.
-npm run dev => runs React only,
-npm run start => runs SB + React.
###
TODO: Proxy is "configured" in FE\package.json, this is to help with communication BE => FE

###


project-root/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   ├── resources/
│   ├── pom.xml
│   └── application.yml
├── frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   ├── webpack.config.js
├── README.md
└── .gitignore
