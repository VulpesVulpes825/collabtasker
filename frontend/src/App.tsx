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
import { useState } from "react";
import AuthService from "@/helper/authentication.ts";

export default function App() {
  const [loggedIn, setLoggedIn] = useState(AuthService.isLoggedIn());
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Layout loggedIn={loggedIn} setLoggedIn={setLoggedIn} />,
      errorElement: <ErrorPage />,
      children: [
        {
          index: true,
          element: <h1>Hello</h1>,
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
              element: <LoginPage setLoggedIn={setLoggedIn} />,
            },
          ],
        },
        {
          element: <PrivateRoutes />,
          children: [
            {
              path: "/dashboard",
              element: <h1>Welcome</h1>,
            },
            {
              path: "/logout",
              element: <Logout setLoggedIn={setLoggedIn} />,
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

  return (
    <RouterProvider router={router} fallbackElement={<p>Initial Load...</p>} />
  );
}
