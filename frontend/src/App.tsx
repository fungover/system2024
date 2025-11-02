import { Outlet } from 'react-router-dom';
import Header from "./components/common/Header";
import Footer from "./components/common/Footer";
import { useTranslation} from 'react-i18next';
import { useTranslationStatus } from './hooks/useTranslationStatus';
import './i18n';
import {useEffect, useState} from "react";
import './index.css'

/**
 * Root application component that renders the layout and manages translation state.
 *
 * The component displays a loading message while translations are loading or an error message if translation loading fails. It listens for i18n language changes and forces a remount when the active language changes so nested routes and components reinitialize. When ready, it renders a Header, an Outlet for nested routes, and a Footer.
 *
 * @returns The application's React element: a container with Header, an Outlet for nested routes, and Footer, or a loading/error message when applicable.
 */
function App() {
    const { i18n } = useTranslation();
    const [key, setKey] = useState(0);
    const { loading, error } = useTranslationStatus();

    useEffect(() => {
        const handleLanguageChange = () => {
            setKey(prevKey => prevKey + 1);
        };
        i18n.on('languageChanged', handleLanguageChange);
        return () => {
            i18n.off('languageChanged', handleLanguageChange);
        };
    }, [i18n]);

  if (loading) {
    return <p>Loading translations...</p>; // Show loading message
  }

  if (error) {
    return <p className="text-red-600">{error}</p>; // Show error message
  }

    return (
        <div className="w-full" key={key}>
            <Header />
            <main className="w-full">
                <Outlet />
            </main>
            <Footer/>
        </div>
    );
}

export default App;