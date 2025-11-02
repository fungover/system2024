import LanguageSelector from '../components/common/LanguageSelector';
import { useTranslation } from 'react-i18next';

/**
 * Renders the home page with a language selector and a localized heading.
 *
 * The heading combines translations for 'jollykey', 'christmaskey', 'andakey', and 'happykey',
 * with the latter two wrapped in emphasized elements styled via `color1` and `color2`.
 *
 * @returns A JSX element containing the page layout: a LanguageSelector and the localized H1 heading.
 */
function HomePage() {
    const { t } = useTranslation();

    return (
        <div className="min-h-screen w-full">
            <LanguageSelector/>
            <h1 className="my-80 text-center text-4xl">
                {t('jollykey')} <strong className="color1">{t('christmaskey')}</strong>
                {t('andakey')} <strong className="color2">{t('happykey')} </strong>!
            </h1>
        </div>
    );
}

export default HomePage;