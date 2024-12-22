import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import './index.css'
import UsersPage from "./pages/UsersPage.tsx";
import App from './App.tsx'
import LoginPage from "./pages/LoginPage.tsx";
import {createBrowserRouter, RouterProvider,} from 'react-router-dom';

const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
    },
    {
        path: '/cake',
        element: <div style={{ fontSize: 150 }}>🍰</div>,
    },
    {
        path:'/users',
        element: <UsersPage/>,
    },
    {
        path: '/auth/login',
        element: <LoginPage/>
    }
]);

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <RouterProvider router={router} />
    </StrictMode>
);