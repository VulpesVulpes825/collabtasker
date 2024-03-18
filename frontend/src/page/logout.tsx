import { useEffect } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import AuthService from "../helper/authentication.ts";

export const Logout = () => {
  const navigate = useNavigate();

  useEffect(() => {
    AuthService.logout()
      .then(() => {
        navigate("/login");
      })
      .catch((error) => {
        console.log(error);
        navigate("/login");
      });
  }, [navigate]);

  return <Navigate to="/login" />;
};
