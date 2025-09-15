import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import HomePage from './pages/HomePage.tsx'
import UsersPage from "./pages/UsersPage.tsx";
import App from './App.tsx'
import LoginPage from "./pages/LoginPage.tsx";
import {createBrowserRouter, RouterProvider,} from 'react-router-dom';
import './i18n';

const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
        children: [
            {
                index: true,
                element: <HomePage />
            },
            {
                path: 'login',
                element: <LoginPage />
            },
            {
                path: 'users',
                element: <UsersPage />
            }
        ]
    }
]);

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <RouterProvider router={router} />
    </StrictMode>
);