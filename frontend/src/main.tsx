import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import UsersPage from "./components/UserPage.tsx";
import App from './App.tsx'
import {
    createBrowserRouter,
    RouterProvider,
} from 'react-router-dom';

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
    }
]);

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <RouterProvider router={router} />
    </StrictMode>
);