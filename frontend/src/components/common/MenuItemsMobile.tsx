type MenuItem = { title: string; url: string };
interface MenuItemsMobile {
  menuItemsMobile: MenuItem[];
}

export default function MenuItemsMobile({ menuItemsMobile }: MenuItemsMobile) {
  return (
    <ul className="space-y-6 text-lg font-semibold">
      {menuItemsMobile.map((menu, index) => (
        <li key={index}>
          <a href={menu.url}>{menu.title}</a>
        </li>
      ))}

      <button className="p-1 w-full  bg-blue-600 text-white rounded-md shadow-md hover:bg-blue-700 ">
        Sign in
      </button>
    </ul>
  );
}
