import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import Header from "./components/common/Header";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <div className="w-screen">
        <Header />
        <main className="w-full">
          <div className="min-h-screen w-full">
            <h1 className="my-40 text-center">WEBPAGE</h1>
          </div>
        </main>
        <footer> footer </footer>
      </div>
    </>
  );
}

export default App;
