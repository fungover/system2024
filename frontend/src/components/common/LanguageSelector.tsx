import React from 'react';
import { useTranslation } from 'react-i18next';

const LanguageSelector: React.FC = () => {
    const { i18n } = useTranslation();

    const changeLanguage = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const newLanguage = event.target.value;

        // Change the language
        i18n.changeLanguage(newLanguage)
            .catch(err => {
                // Remove debug logging in production
                // Console logging should be removed or conditionally enabled only in development.
                if (process.env.NODE_ENV === 'development') {
                    console.error(`Failed to change language to ${newLanguage}`, err);
                }
            });
    };

    return (
        <div>
            <select
                value={i18n.language}
                onChange={changeLanguage}
                aria-label="Select language"
                disabled={i18n.isInitialized === false}
            >
            <option value="en">English</option>
            <option value="es">Español</option>
            <option value="sv">Svenska</option>
        </select>
</div>
)
    ;
};

export default LanguageSelector;
