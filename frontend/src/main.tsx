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
        errorElement: <ErrorPage />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
);
