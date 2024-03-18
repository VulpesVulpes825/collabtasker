import { Card, CardTitle } from "@/components/ui/card";
import { Checkbox } from "@/components/ui/checkbox";
import { TrashIcon } from "@radix-ui/react-icons";
import { useEffect, useState } from "react";
import ItemService from "../helper/item-service.ts";

interface props {
  id: string;
  title: string;
  content: string;
  until: Date;
  complete: boolean;
}

export default function TodoItem({ id }: props) {
  const [complete, setComplete] = useState(false);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [until, setUntil] = useState(undefined);

  const setData = async () => {
    ItemService.setItem(id, title, content, until, complete);
  };

  useEffect(() => {
    const fetchData = async () => {
      const response = await ItemService.getItem(id);
      const data = response.data;
      setComplete(data.complete);
      setTitle(data.title);
      setContent(data.content);
      setUntil(data.until);
    };
    fetchData().catch((error) => {
      console.error(error);
    });
  }, []);

  function toggleFinished() {
    setComplete(!complete);
    ItemService.setItem(id, title, content, until, !complete);
  }

  return (
    <>
      <Card className="min-w-80">
        <CardTitle className="flex items-center justify-between text-xl">
          <Checkbox checked={complete} onCheckedChange={toggleFinished} />
          {complete ? <del>{title}</del> : title}
          <TrashIcon />
        </CardTitle>
      </Card>
    </>
  );
}
