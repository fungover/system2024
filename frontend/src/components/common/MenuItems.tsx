import { Link } from 'react-router-dom';
type MenuItem = { title: string; url: string };

interface MenuItems {
  menuItems: MenuItem[];
}

/**
 * Render a horizontal navigation menu from an array of menu items with a persistent Login link.
 *
 * Each item in `menuItems` is rendered as a router `Link` to its `url`. A Login button linking to `/login`
 * is appended after the mapped menu items.
 *
 * @param menuItems - Array of menu entries where each entry has a `title` and `url`
 * @returns A navigation element containing the horizontal menu and the Login link
 */
export default function MenuItems({ menuItems }: MenuItems) {
    return (
        <nav className="h-full w-full font-semibold">
            <ul className="h-full flex gap-8">
                {menuItems.map((menu, index) => (
                    <li
                        key={index}
                        className="h-full flex items-center border-b-2 border-white hover:border-purple-800"
                    >
                        <Link to={menu.url} className="text-black text-xl">
                            {menu.title}
                        </Link>
                    </li>
                ))}
                <Link to="/login">
                    <button className="p-1 w-24 h-2/3 self-center bg-blue-600 text-white rounded-md shadow-md hover:bg-blue-700">
                        <p className="text-lg">Login</p>
                    </button>
                </Link>
            </ul>
        </nav>
    );
}