import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

i18n.use(initReactI18next).init({
    resources: {
        en: {
            translation: {
                "welcome": "Welcome to React",
                "changeLanguage": "Change Language",
                "visitCake": "Visit /cake",
                "cakeText": "Enjoy a delicious cake!"
            },
        },
        sv: {
            translation: {
                "welcome": "Välkommen till React",
                "changeLanguage": "Byt språk",
                "visitCake": "Besök /cake",
                "cakeText": "Njut av en läcker tårta!"
            },
        },
        es: {
            translation: {
                "welcome": "Bienvenido a React",
                "changeLanguage": "Cambiar idioma",
                "visitCake": "Visitar /cake",
                "cakeText": "¡Disfruta de una deliciosa tarta!"
            },
        },
    },
    lng: "en", // Default language
    fallbackLng: "en", // Fallback language
    interpolation: {
        escapeValue: false, // React already does escaping
    },
});

export default i18n;
