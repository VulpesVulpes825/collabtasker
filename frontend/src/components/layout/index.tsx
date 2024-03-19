import Header from "@/components/layout/header";
import { Outlet } from "react-router-dom";
import { useEffect, useState } from "react";

interface LayoutProps {
  loggedIn: boolean;
  setLoggedIn: (value: ((prevState: boolean) => boolean) | boolean) => void;
}

export default function Layout({ loggedIn, setLoggedIn }: LayoutProps) {
  const [login, setLogin] = useState(loggedIn);
  useEffect(() => {
    setLogin(loggedIn);
  }, [loggedIn]);
  return (
    <>
      <Header loggedIn={login} setLoggedIn={setLoggedIn} />
      <div className="flex h-screen border-collapse overflow-hidden">
        <main className="flex-1 overflow-y-auto overflow-x-hidden pt-16 bg-secondary/10 pb-1">
          <Outlet />
        </main>
      </div>
    </>
  );
}
