import { Link, useRouteError } from "react-router-dom";
import { Button } from "@/components/ui/button";

export default function ErrorPage() {
  const error = useRouteError();
  console.error(error);

  return (
    <div className="min-h-screen flex flex-grow items-center justify-center bg-gray-50">
      <div className="rounded-lg bg-white p-8 text-center shadow-xl">
        <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl">
          Oops!
        </h1>
        <p className="leading-7 [&:not(:first-child)]:mt-6">
          Sorry, an unexpected error has occurred.
        </p>
        <p className="leading-7 [&:not(:first-child)]:mt-6">
          <i>{error.statusText || error.message}</i>
        </p>
        <Button>
          <Link to="/dashboard">Back Home</Link>
        </Button>
      </div>
    </div>
  );
}
