import { useTranslation } from 'react-i18next';
import { useState, useEffect } from 'react';

function CakePage() {
    const { t, ready, i18n } = useTranslation();
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Listen to the i18n loading and error events
        const handleLoaded = () => {
            if (!i18n.languages || i18n.languages.length === 0) {
                setError('No languages loaded');
            }
        };

        const handleError = (lng: string, ns: string, msg: string) => {
            setError(`Error loading translation for ${lng} - ${ns}: ${msg}`);
        };

        i18n.on('loaded', handleLoaded);
        i18n.on('failedLoading', handleError);

        return () => {
            i18n.off('loaded', handleLoaded);
            i18n.off('failedLoading', handleError);
        };
    }, [i18n]);

    if (!ready) {
        return <div>Loading translations...</div>;
    }

    if (error) {
        return <div>Error loading translations: {error}</div>;
    }

    return (
        <div style={{ textAlign: 'center', fontSize: 30 }}>
            <div>
                <span role="img" aria-label="cake" style={{ fontSize: '100px' }}>
                    🍰
                </span>
            </div>
            <p>{t('cakeText')}</p> {}
        </div>
    );
}

export default CakePage;
