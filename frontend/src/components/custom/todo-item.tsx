import { Card, CardTitle } from "../ui/card.tsx";
import { Checkbox } from "../ui/checkbox.tsx";
import { Pencil, TrashIcon } from "lucide-react";
import { useEffect, useState } from "react";
import ItemService from "../../helper/item-service.ts";
import { Button } from "../ui/button.tsx";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "../ui/dialog.tsx";
import { Input } from "../ui/input.tsx";
import { Label } from "../ui/label.tsx";

interface props {
  id: string;
  offlineTitle: string;
  content: string;
  until: Date;
  complete: boolean;
  removeItem;
  online: boolean;
}

export default function TodoItem({
  id,
  removeItem,
  online,
  offlineTitle,
}: props) {
  const [complete, setComplete] = useState(false);
  const [title, setTitle] = useState(offlineTitle);
  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState("");
  const [until, setUntil] = useState(undefined);

  useEffect(() => {
    if (online) {
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
    }
  }, []);

  function toggleFinished() {
    ItemService.setItem(id, title, content, until, !complete)
      .then(setComplete(!complete))
      .catch((error) => {
        console.error(error);
      });
  }

  const handleDoubleClick = () => {
    setIsEditing(true);
  };

  const handleBlur = () => {
    if (online) {
      ItemService.setItem(id, title, content, until, complete)
        .then(setIsEditing(false))
        .catch((error) => {
          console.error(error);
        });
    } else {
      setIsEditing(false);
    }
  };

  const handleChange = (event) => {
    setTitle(event.target.value);
  };

  function markFinished() {
    if (complete) {
      return <del className="text-slate-400">{title}</del>;
    }
    return isEditing ? (
      <Input
        type="text"
        value={title}
        onChange={handleChange}
        onBlur={handleBlur}
      />
    ) : (
      <div onDoubleClick={handleDoubleClick}>{title}</div>
    );
  }

  return (
    <Card className="min-w-80 p-3 m-2 min-w-96">
      <CardTitle className="flex items-center justify-between text-xl">
        <Checkbox checked={complete} onCheckedChange={toggleFinished} />
        {markFinished()}
        <div>
          <Button variant="outline" size="icon" onClick={() => removeItem(id)}>
            <TrashIcon />
          </Button>
          <Dialog>
            <DialogTrigger asChild>
              <Button variant="outline" size="icon">
                <Pencil />
              </Button>
            </DialogTrigger>
            <DialogContent className="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>Edit iem</DialogTitle>
                <DialogDescription>
                  Make changes to your item here. Click save when you're done.
                </DialogDescription>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="name" className="text-right">
                    Name
                  </Label>
                  <Input
                    id="name"
                    value="Pedro Duarte"
                    className="col-span-3"
                  />
                </div>
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="username" className="text-right">
                    Username
                  </Label>
                  <Input
                    id="username"
                    value="@peduarte"
                    className="col-span-3"
                  />
                </div>
              </div>
              <DialogFooter>
                <Button type="submit">Save changes</Button>
              </DialogFooter>
            </DialogContent>
          </Dialog>
        </div>
      </CardTitle>
    </Card>
  );
}
