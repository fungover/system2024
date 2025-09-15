import LanguageSelector from '../components/common/LanguageSelector';
import { useTranslation } from 'react-i18next';

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