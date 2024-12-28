import Header from "./components/common/Header";
import Footer from "./components/common/Footer";
import LanguageSelector from './components/common/LanguageSelector';
import { useTranslation} from 'react-i18next';
import { useTranslationStatus } from './hooks/useTranslationStatus';
import './i18n';
import {useEffect, useState} from "react";
import './index.css'

function App() {
  const { t, i18n } = useTranslation();
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
    <>
      <div className="w-full" key={key}>
        <Header />
        <main className="w-full">
          <div className="min-h-screen w-full">
            <LanguageSelector/>

            <h1 className="my-80 text-center text-4xl"> {t('jollykey')} <strong
                className="color1">{t('christmaskey')}</strong> {t('andakey')} <strong
                className="color2">{t('happykey')} </strong>!</h1>
      </div>
    </main>
  <Footer/>
</div>
</>
)
  ;
}

export default App;
