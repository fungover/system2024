import React from 'react';
import { useTranslation } from 'react-i18next';

const LanguageSelector: React.FC = () => {
    const { i18n } = useTranslation();

    const changeLanguage = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const newLanguage = event.target.value;
        console.log('Changing language to:', newLanguage);
        i18n.changeLanguage(newLanguage)
            .then(() => {
                console.log(`Language changed to ${newLanguage}`);
            })
            .catch(err => {
                console.error(`Failed to change language to ${newLanguage}`, err);
            });
    };

    return (
        <div>
            <select value={i18n.language} onChange={changeLanguage}>
                <option value="en">English</option>
                <option value="es">Español</option>
                <option value="sv">Svenska</option>
            </select>
        </div>
    );
};

export default LanguageSelector;
