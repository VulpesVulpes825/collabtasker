import { Navigate, Outlet, useLocation } from "react-router-dom";
import AuthService from "../helper/authentication.ts";

export const PublicRoutes = () => {
  const location = useLocation();
  if (AuthService.isLoggedIn()) {
    return <Navigate to="/dashboard" replace state={{ from: location }} />;
  }
  return <Outlet />;
};
