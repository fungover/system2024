import { FaInstagram } from "react-icons/fa";
import { FaGithub } from "react-icons/fa";
import { FaTiktok } from "react-icons/fa";
import FootermenuItems from "./FootermenuItems";
import {t} from "i18next";
import {useTranslationStatus} from "../../hooks/useTranslationStatus.tsx";

export default function Footer() {
  const { loading, error } = useTranslationStatus();

  if (loading) {
    return <p>Loading translations...</p>; // Show loading message
  }

  if (error) {
    return <p className="text-red-600">{error}</p>; // Show error message
  }

  return (
    <div className="flex sm:flex-row flex-col w-full min-h-52 bg-gray-800 text-white pl-10 space-y-6 sm:space-y-0 sm:justify-center sm:gap-8 md:gap-16 lg:gap-24 xl:gap-32 2xl:gap-40 3xl:gap-48 py-10">
      <div className="min-[520px]:flex min-[520px]:justify-between min-[520px]:pr-16 sm:pr-0 sm:gap-8 md:gap-16 lg:gap-24 xl:gap-32 2xl:gap-40 3xl:gap-48">
        <div className="mb-5 sm:mb-0">
          <h2 className="text-xl font-bold mb-2">{t('navkey')}</h2>
          <FootermenuItems
            menuItems={[
              { title: t("homekey"), url: "#" },
              { title: t("aboutkey"), url: "#" },
              { title: t("contactkey"), url: "#" },
            ]}
          />
        </div>
        <div className="">
          <h2 className="text-xl font-bold mb-2">{t('mediakey')}</h2>
          <ul className="text-3xl flex gap-4">
            <li>
              <FaGithub />
            </li>
            <li>
              <FaInstagram />
            </li>
            <li>
              <FaTiktok />
            </li>
          </ul>
        </div>
      </div>
      <div className="">
        <h2 className="text-xl font-bold mb-2">{t('contactkey')}</h2>
        <ul>
          <li>
            <span className="font-bold">{t('addresskey')}</span> 123 Road, Stockholm
            Sweden
          </li>
          <li>
            <span className="font-bold">{t('phonekey')}</span> 0123456789
          </li>
          <li>
            <span className="font-bold">{t('emailkey')}</span> test@test.se
          </li>
        </ul>
      </div>
    </div>
  );
}
