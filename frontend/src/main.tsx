import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import {
  BoardPage,
  ErrorPage,
  LoginPage,
  Logout,
  RegisterPage,
} from "./components/pages";
import { PrivateRoutes, PublicRoutes } from "./route";
import BoardService from "./helper/board-service.ts";
import Layout from "@/components/layout";
import { ThemeProvider } from "@/components/custom/theme-provider";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    errorElement: <ErrorPage />,
    children: [
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
          {
            path: "/logout",
            element: <Logout />,
          },
          {
            path: "/boards/:id",
            loader: ({ params }) => {
              console.log(params.id);
              return BoardService.getBoard(params.id);
            },
            element: <BoardPage />,
          },
        ],
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>,
);
