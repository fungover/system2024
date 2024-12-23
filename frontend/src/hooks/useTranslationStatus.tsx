import { useState, useEffect } from "react";
import i18n from "i18next";
import { useTranslation } from "react-i18next";

export const useTranslationStatus = () => {
    const { t } = useTranslation();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const handleLoading = () => setLoading(true);
        const handleLoaded = () => setLoading(false);
        const handleError = (lng: string, ns: string, msg: string) => {
            setError(t("messages.error.translation", { lng, ns, msg }));
            setLoading(false);
        };

        i18n.on("loading", handleLoading);
        i18n.on("loaded", handleLoaded);
        i18n.on("failedLoading", handleError);

        return () => {
            i18n.off("loading", handleLoading);
            i18n.off("loaded", handleLoaded);
            i18n.off("failedLoading", handleError);
        };
    }, [t]);

    return { loading, error };
};
