import React from "react";
import { useTranslationStatus } from "../hooks/useTranslationStatus";

const TranslationHandler: React.FC = () => {
    const { loading, error } = useTranslationStatus();

    if (loading) {
        return <p>Loading translations...</p>;
    }

    if (error) {
        return <p className="text-red-600">{error}</p>;
    }

    return <p>Translations loaded successfully!</p>;
};

export default TranslationHandler;
