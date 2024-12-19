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
              <div role="radiogroup" aria-label={t('changeLanguage')}>
                  {['en', 'sv', 'es'].map((lang) => (
                      <button
                          key={lang}
                          onClick={() => handleChangeLanguage(lang)}
                          aria-pressed={i18n.language === lang}
                          aria-label={t(`languages.${lang}`)} // Translation for the language name
                          className={i18n.language === lang ? 'active' : ''}
                      >
                          {t(`languages.${lang}`)} {/* Translated language name */}
                      </button>
                  ))}
              </div>
          </div>

          <div className="card">
              <Link to='/cake'>{t('visitCake')}</Link> {/* Translated visitCake text */}
          </div>
      </>
  );
}

export default App;
