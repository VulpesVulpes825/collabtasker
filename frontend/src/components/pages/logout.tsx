import { useEffect } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import AuthService from "@/helper/authentication.ts";

interface LogoutProps {
  setLoggedIn: (value: ((prevState: boolean) => boolean) | boolean) => void;
}

export default function Logout({ setLoggedIn }: Readonly<LogoutProps>) {
  const navigate = useNavigate();

  useEffect(() => {
    AuthService.logout()
      .then(() => {
        console.log("Logged out");
        setLoggedIn(false);
        navigate("/");
      })
      .catch((error) => {
        console.log(error);
        setLoggedIn(false);
        navigate("/");
      });
  }, [navigate]);

  return <Navigate to="/login" />;
}
