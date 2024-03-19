import Header from "@/components/layout/header";
import { Outlet } from "react-router-dom";

export default function Layout() {
  return (
    <>
      <Header />
      <div className="flex h-screen border-collapse overflow-hidden">
        <main className="flex-1 overflow-y-auto overflow-x-hidden pt-16 bg-secondary/10 pb-1">
          <Outlet />
        </main>
      </div>
    </>
  );
}
