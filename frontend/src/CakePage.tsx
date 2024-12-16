import { useTranslation } from 'react-i18next';

function CakePage() {
    const { t } = useTranslation(); // Use the translation hook to access translations

    return (
        <div style={{ textAlign: 'center', fontSize: 30 }}>
            <div>
        <span role="img" aria-label="cake" style={{ fontSize: '100px' }}>
          🍰
        </span>
            </div>
            <p>{t('cakeText')}</p> {/* Translated text for the cake page */}
        </div>
    );
}

export default CakePage;