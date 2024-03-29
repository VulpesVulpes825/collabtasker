import { Navigate, Outlet, useLocation } from "react-router-dom";
import AuthService from "../helper/authentication.ts";

export const PrivateRoutes = () => {
  const location = useLocation();
  console.log(AuthService.isLoggedIn());
  if (!AuthService.isLoggedIn()) {
    return <Navigate to="/" replace state={{ from: location }} />;
  }
  return <Outlet />;
};
