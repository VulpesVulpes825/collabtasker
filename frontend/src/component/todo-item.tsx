import { Card, CardTitle } from "@/components/ui/card";
import { Checkbox } from "@/components/ui/checkbox";
import { TrashIcon } from "lucide-react";
import { useEffect, useState } from "react";
import ItemService from "../helper/item-service.ts";
import { Button } from "@/components/ui/button.tsx";

interface props {
  id: string;
  title: string;
  content: string;
  until: Date;
  complete: boolean;
  removeItem;
}

export default function TodoItem({ id, removeItem }: props) {
  const [complete, setComplete] = useState(false);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [until, setUntil] = useState(undefined);

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
    ItemService.setItem(id, title, content, until, !complete)
      .then(setComplete(!complete))
      .catch((error) => {
        console.error(error);
      });
  }

  return (
    <Card className="min-w-80 p-3 m-2">
      <CardTitle className="flex items-center justify-between text-xl">
        <Checkbox checked={complete} onCheckedChange={toggleFinished} />
        {complete ? <del className="text-slate-400">{title}</del> : title}
        <Button variant="outline" size="icon" onClick={() => removeItem(id)}>
          <TrashIcon />
        </Button>
      </CardTitle>
    </Card>
  );
}
