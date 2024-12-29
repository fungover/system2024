
type MenuItem = { title: string; url: string };

interface MenuItems {
  menuItems: MenuItem[];
}

export default function MenuItems({ menuItems }: MenuItems) {
  return (
    <nav className="h-full w-full ml-8 font-semibold">
      <ul className="h-full flex gap-8">
        {menuItems.map((menu, index) => (
          <li
            key={index}
            className="h-full flex items-center border-b-2 border-white hover:border-purple-800"
          >
            <a href={menu.url} className="text-black text-xl">
                {menu.title}
            </a>
          </li>
        ))}
        <button className="p-1 w-24 h-2/3 self-center bg-blue-600 text-white rounded-md shadow-md hover:bg-blue-700">
          <p className="text-lg">Login</p>
        </button>
      </ul>
    </nav>
  );
}
