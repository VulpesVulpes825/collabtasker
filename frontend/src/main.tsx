import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import { DevSupport } from "@react-buddy/ide-toolbox";
import { ComponentPreviews, useInitial } from "./dev";
import ErrorPage from "./page/error-page.tsx";
import { PrivateRoutes } from "./route/privateRoutes.tsx";
import { PublicRoutes } from "./route/publicRoutes.tsx";

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
