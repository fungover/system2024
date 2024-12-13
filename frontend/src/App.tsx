// import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './App.css'

function App() {
    return (
        <div>
            <h1>Welcome!</h1>
            <p>Click the button below to sign in with GitHub</p>
            <a href={"/oauth2/authorization/github"}>
                <button>Sign in with GitHub</button>
            </a>
        </div>
    );
}

export default App;
