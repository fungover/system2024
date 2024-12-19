type menuItems = {
  title: string;
  url: string;
};

interface FootermenuItemsProps {
  menuItems: menuItems[];
}

export default function FootermenuItems({ menuItems }: FootermenuItemsProps) {
  return (
    <ul>
      {menuItems.map((item) => (
        <li key={item.title}>
          <a href={item.url}>{item.title}</a>
        </li>
      ))}
    </ul>
  );
}
