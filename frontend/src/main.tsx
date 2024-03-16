import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import { ErrorPage, LoginPage, RegisterPage } from "./page";
import { PrivateRoutes, PublicRoutes } from "./route";

const router = createBrowserRouter([
  {
    path: "/",
    element: <h1>Hello World.</h1>,
    errorElement: <ErrorPage />,
  },
  {
    element: <PublicRoutes />,
    children: [
      {
        path: "/signup",
        element: <RegisterPage />,
      },
      {
        path: "/login",
        element: <LoginPage />,
      },
    ],
  },
  {
    element: <PrivateRoutes />,
    children: [
      {
        path: "/dashboard",
        element: <App />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
);
