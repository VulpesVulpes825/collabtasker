import { Navigate, Outlet, useLocation } from "react-router-dom";
import AuthService from "../helper/authentication.ts";
import React from "react";

export const PrivateRoutes = () => {
  const location = useLocation();
  if (!AuthService.isLoggedIn()) {
    return <Navigate to="/login" replace state={{ from: location }} />;
  }
  return <Outlet />;
};