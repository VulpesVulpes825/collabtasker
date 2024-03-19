import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import {
  BoardPage,
  ErrorPage,
  IndexPage,
  LoginPage,
  Logout,
  RegisterPage,
} from "@/components/pages";
import { PrivateRoutes, PublicRoutes } from "./route";
import BoardService from "./helper/board-service.ts";
import Layout from "@/components/layout";
import { useState } from "react";
import AuthService from "@/helper/authentication.ts";
import { ThemeProvider } from "@/components/custom/theme-provider.tsx";
import Dashboard from "@/components/pages/dashboard.tsx";

export default function App() {
  const [loggedIn, setLoggedIn] = useState(AuthService.isLoggedIn());
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Layout loggedIn={loggedIn} />,
      errorElement: <ErrorPage />,
      children: [
        {
          element: <PublicRoutes />,
          children: [
            {
              index: true,
              element: <IndexPage />,
            },
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
              loader: () => {
                return BoardService.getBoards();
              },
              element: <Dashboard />,
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
    <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
      <RouterProvider
        router={router}
        fallbackElement={<p>Initial Load...</p>}
      />
    </ThemeProvider>
  );
}
