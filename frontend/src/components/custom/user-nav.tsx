import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Button } from "@/components/ui/button";
import { LogOut, Settings, User as UserIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";

interface Props {
  user: string;
}

export function UserNav({ user, setLogout }: Readonly<Props>) {
  const navigate = useNavigate();

  return (
    <DropdownMenu>
      <DropdownMenuTrigger>
        <Button
          variant="ghost"
          size="icon"
          className="h-9 w-9 rounded-md border"
        >
          <UserIcon className="h-4 w-4 rotate-0 scale-100" />
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent align="end">
        <div className="flex items-center justify-start gap-4 p-2">
          <div className="flex flex-col space-y-1 leading-none">
            {user && <p className="font-medium">{user}</p>}
          </div>
        </div>
        <DropdownMenuSeparator />
        <DropdownMenuItem asChild>
          <Button
            variant="outline"
            className="w-full"
            onClick={() => {
              navigate("/setting");
            }}
          >
            <Settings className="mr-2 h-4 w-4" aria-hidden="true" />
            Setting
          </Button>
        </DropdownMenuItem>
        <DropdownMenuItem asChild>
          <Button
            variant="outline"
            className="w-full"
            onClick={() => {
              setLogout(false);
              navigate("/logout");
            }}
          >
            <LogOut className="mr-2 h-4 w-4" aria-hidden="true" />
            Log Out
          </Button>
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
