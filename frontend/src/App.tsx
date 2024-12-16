import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import {Link} from "react-router-dom";
import { useTranslation } from 'react-i18next';

function App() {
    const { t, i18n } = useTranslation();

    const handleChangeLanguage = (lang: string) => {
        i18n.changeLanguage(lang);
    };

  return (
      <>
          <div>
              <a href="https://vite.dev" target="_blank">
                  <img src={viteLogo} className="logo" alt="Vite logo"/>
              </a>
              <a href="https://react.dev" target="_blank">
                  <img src={reactLogo} className="logo react" alt="React logo"/>
              </a>
          </div>
          <h1>{t('welcome')}</h1> {/* Translated welcome text */}

          <div>
              <button onClick={() => handleChangeLanguage('en')}>English</button>
              <button onClick={() => handleChangeLanguage('sv')}>Svenska</button>
              <button onClick={() => handleChangeLanguage('es')}>Español</button>
          </div>

          <div className="card">
              <Link to='/cake'>{t('visitCake')}</Link> {}
          </div>
      </>
  );
}

export default App;
