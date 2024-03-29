import { Link, useNavigate } from "react-router-dom";
import { ThemeToggle } from "@/components/custom/theme-toggle.tsx";
import { ListTodo } from "lucide-react";
import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import AuthService from "@/helper/authentication.ts";
import { UserNav } from "@/components/custom/user-nav.tsx";

interface HeaderProps {
  loggedIn: boolean;
}

export default function Header({ loggedIn }: HeaderProps) {
  const navigate = useNavigate();
  const [login, setLogin] = useState(loggedIn);

  useEffect(() => {
    console.log("Changing logged in status to ", loggedIn);
    setLogin(loggedIn);
  }, [loggedIn]);

  function getButton() {
    if (login) {
      return <UserNav user={AuthService.getFullName()} setLogout={setLogin} />;
    } else {
      return (
        <>
          <Button
            size="sm"
            onClick={() => {
              navigate("/signup");
            }}
          >
            Sign up
          </Button>
          <Button
            size="sm"
            onClick={() => {
              navigate("/login");
            }}
          >
            Log in
          </Button>
        </>
      );
    }
  }

  return (
    <div className="supports-backdrop-blur:bg-background/60 fixed left-0 right-0 top-0 z-20 border-b bg-background/95 backdrop-blur">
      <nav className="flex h-16 items-center justify-between px-4">
        <Link
          to={login ? "/dashboard" : "/"}
          className="items-center justify-between gap-2 flex-row flex"
        >
          <ListTodo className="h-6 w-6" />
          <h1 className="text-lg font-semibold">CollabTasker</h1>
        </Link>
        <div className="flex items-center gap-2">
          <ThemeToggle />
          {getButton()}
        </div>
      </nav>
    </div>
  );
}
