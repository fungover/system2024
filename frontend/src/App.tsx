import Header from "./components/common/Header";

function App() {
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
