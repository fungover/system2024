import Header from "./components/common/Header";
import Footer from "./components/common/Footer";


function App() {
  return (
    <>
      <div className="w-full">
        <Header />
        <main className="w-full">
          <div className="min-h-screen w-full">
            <h1 className="my-40 text-center">WEBPAGE</h1>
          </div>
        </main>
        <Footer />
      </div>
    </>
  );
}

export default App;
