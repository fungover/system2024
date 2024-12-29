import {useEffect, useState} from "react";
import { FaBars } from "react-icons/fa";
import MenuItems from "./MenuItems";
import MenuItemsMobile from "./MenuItemsMobile";
import LanguageSelector from './LanguageSelector';
import {useTranslation} from "react-i18next";
import { useTranslationStatus } from '../../hooks/useTranslationStatus';
import i18n from "i18next";

export default function Header() {
  const [isMobile, setIsMobile] = useState(false);
  const { t } = useTranslation();
  const { loading, error } = useTranslationStatus();

  const handleMobileMenu = () => {
    setIsMobile(!isMobile);
  };

  useEffect(() => {
    console.log(t('Home')); // Log to check if translation is correctly loaded
  }, [i18n.language]);

  if (loading) {
    return <p>Loading translations...</p>; // Show loading message
  }

  if (error) {
    return <p className="text-red-600">{error}</p>; // Show error message
  }

  const menuItems = [
    {
      title: t("homekey"),
      url: "/",
    },
    {
      title: t("aboutkey"),
      url: "/",
    },
    {
      title: t("contactkey"),
      url: "/",
    },
  ];

  return (
    <>
      <header className="w-full bg-white border-b-2 shadow-sm relative z-20 h-16 px-4">
        <div className="flex justify-between h-full ">
          <div className="h-full flex items-center">
            <h1 className="text-2xl font-semibold font-serif">LOGO</h1>
          </div>
          <div className="flex flex-row align-center h-full">
            <div className="hidden md:flex items-center h-full mr-10">
              <nav className="h-full w-full font-semibold">
                <ul className="h-full flex gap-8">
                  <li className="h-full flex items-center border-b-2 border-white hover:border-purple-800">
                    <a href="#" className="text-black text-xl">
                      {t('homekey')}
                    </a>
                  </li>
                  <li className="h-full flex items-center border-b-2 border-white hover:border-purple-800">
                    <a href="#" className="text-black text-xl">
                      {t('aboutkey')}
                    </a>
                  </li>
                  <li className="h-full flex items-center border-b-2 border-white hover:border-purple-800">
                    <a href="#" className="text-black text-xl">
                      {t('contactkey')}
                    </a>
                  </li>
                  <li className="h-full flex items-center">
                    <button className="p-1 w-24 bg-blue-600 text-white rounded-md shadow-md hover:bg-blue-700">
                      <p className="text-lg">{t('loginkey')}</p>
                    </button>
                  </li>
                </ul>
              </nav>
              <LanguageSelector />
              <MenuItems menuItems={menuItems} />
            </div>
            <div className="flex items-center">
              <img
                src="/bild.png"
                alt="Avatar"
                className="rounded-full w-10 sm:w-12 md:mr-6"
              />
              <button onClick={handleMobileMenu} className="md:hidden mx-5">
                {" "}
                <FaBars size={30} />{" "}
              </button>
            </div>
          </div>
        </div>
      </header>
      <div
        className={`${
          isMobile
            ? "opacity-100 pointer-events-auto translate-y-0 duration-200"
            : "opacity-0 pointer-events-none -translate-y-full duration-0"
        } transition-all ease-out absolute left-0 right-0 z-10 border-2 border-t-0 shadow-sm bg-white p-6 pt-8 rounded-sm md:hidden`}
      >
        <ul className="space-y-6 text-lg font-semibold">
          <li>
            <a href="#">{t('homekey')}</a>
          </li>
          <li>
            <a href="#">{t('aboutkey')}</a>
          </li>
          <li>
            <a href="#">{t('contactkey')}</a>
          </li>
          <li>
            <button className="p-1 w-full  bg-blue-600 text-white rounded-md shadow-md hover:bg-blue-700 ">
              {t('loginkey')}
            </button>
          </li>
        </ul>
      </div>
    </>
  );
}
