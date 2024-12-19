import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import UsersPage from "./pages/UsersPage.tsx";
import App from './App.tsx'
import {
    createBrowserRouter,
    RouterProvider,
} from 'react-router-dom';
import './i18n';
import CakePage from './CakePage';

const router = createBrowserRouter([
    {
        path: '/',
        element: <App />,
    },
    {
        path: '/cake',
        element: <CakePage />,
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