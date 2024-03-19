import { Link } from "react-router-dom";
import { ThemeToggle } from "@/components/custom/theme-toggle.tsx";
import { ListTodo } from "lucide-react";
import { cn } from "@/components/lib/utils";
import AuthService from "@/helper/authentication";
import { Button } from "@/components/ui/button";

export default function Header() {
  return (
    <div className="supports-backdrop-blur:bg-background/60 fixed left-0 right-0 top-0 z-20 border-b bg-background/95 backdrop-blur">
      <nav className="flex h-16 items-center justify-between px-4">
        <Link
          to={"/"}
          className="hidden items-center justify-between gap-2 md:flex"
        >
          <ListTodo className="h-6 w-6" />
          <h1 className="text-lg font-semibold">CollabTasker</h1>
        </Link>
        <div className={cn("block md:!hidden")}>{/*<MobileSidebar />*/}</div>

        <div className="flex items-center gap-2">
          <ThemeToggle />
          {AuthService.isLoggedIn() ? (
            <p>Logged in</p>
          ) : (
            <Button
              size="sm"
              onClick={() => {
                <Link to="/login" />;
              }}
            >
              Sign In
            </Button>
          )}
        </div>
      </nav>
    </div>
  );
}
