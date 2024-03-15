import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import { DevSupport } from "@react-buddy/ide-toolbox";
import { ComponentPreviews, useInitial } from "./dev";
import { ErrorPage, RegisterPage } from "./page";
import { PrivateRoutes, PublicRoutes } from "./route";

const router = createBrowserRouter([
  {
    path: "/",
    element: <h1>Hello World.</h1>,
    errorElement: <ErrorPage />
  },
  {
    path: "/login",
    element: <PublicRoutes />
  },
  {
    path: "/signup",
    element: <RegisterPage />
  },
  {
    element: <PrivateRoutes />,
    children: [
      {
        path: "/dashboard",
        element: <App />
      }
    ]
  }
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <DevSupport
      ComponentPreviews={ComponentPreviews}
      useInitialHook={useInitial}
    >
      <RouterProvider router={router} />
    </DevSupport>
  </React.StrictMode>
);
