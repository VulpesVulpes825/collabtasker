import { useRouteError } from "react-router-dom";
import { Button, Result } from "antd";

export default function ErrorPage() {
  const error = useRouteError();
  console.log(error);

  return (
    <Result
      status="error"
      title="Sorry, an unexpected error has occurred."
      subTitle={
        (error as Error)?.message ||
        (error as { statusText?: string })?.statusText
      }
      extra={<Button type="primary">Back Home</Button>}
    />
  );
}
