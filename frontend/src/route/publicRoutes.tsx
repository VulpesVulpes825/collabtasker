import { Navigate, useLocation } from "react-router-dom";
import AuthService from "../helper/authentication.ts";
import LoginPage from "../page/login-page.tsx";
import React from "react";

export const PublicRoutes = () => {
  const location = useLocation();
  if (AuthService.isLoggedIn()) {
    return <Navigate to="/dashboard" replace state={{ from: location }} />;
  }
  return <LoginPage />;
};