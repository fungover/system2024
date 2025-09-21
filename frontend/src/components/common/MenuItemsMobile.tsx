import { Link } from 'react-router-dom';

type MenuItem = { title: string; url: string };
interface MenuItemsMobile {
    menuItemsMobile: MenuItem[];
}

export default function MenuItemsMobile({ menuItemsMobile }: MenuItemsMobile) {
    return (
        <ul className="space-y-6 text-lg font-semibold">
            {menuItemsMobile.map((menu) => (
                <li key={menu.url}>
                    <Link to={menu.url}>{menu.title}</Link>
                </li>
            ))}

            <li>
                <Link
                    to="/login"
                    className="p-1 w-full inline-flex items-center justify-center bg-blue-600 text-white rounded-md shadow-md hover:bg-blue-700"
                >
                    Sign in
                </Link>
            </li>
        </ul>
    );
}