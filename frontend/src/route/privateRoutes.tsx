import { Navigate, Outlet, useLocation } from "react-router-dom";
import AuthService from "../helper/authentication.ts";

export const PrivateRoutes = () => {
  const location = useLocation();
  if (!AuthService.isLoggedIn()) {
    return <Navigate to="/" replace state={{ from: location }} />;
  }
  return <Outlet />;
};
