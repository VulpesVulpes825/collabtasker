import { Card, CardTitle } from "@/components/ui/card";

interface props {
  title: string;
}

export default function TodoItem({ title }: props) {
  return (
    <>
      <Card>
        <CardTitle className="flex items-center justify-center text-xl">
          {title}
        </CardTitle>
      </Card>
    </>
  );
}
