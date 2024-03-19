import { useLoaderData } from "react-router-dom";
import TodoItem from "@/components/custom/todo-item.tsx";
import { Plus } from "lucide-react";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import BoardService from "@/helper/board-service.ts";
import ItemService from "@/helper/item-service.ts";

export default function BoardPage() {
  const data = useLoaderData();

  const [inputValue, setInputValue] = useState("");
  const [tasks, setTasks] = useState(data.items);

  function addItem(): void {
    BoardService.setItem(data.id, inputValue)
      .then((response) => {
        const data = response.data;
        const newTask = {
          id: data.id,
          key: data.id,
        };
        setTasks([...tasks, newTask]);
        setInputValue("");
      })
      .catch((error) => {
        console.error(error);
      });
  }

  function removeItem(id: string): void {
    console.log("Clicked");
    ItemService.deleteItem(id)
      .then(() => {
        setTasks(tasks.filter((task) => task.id !== id));
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen lg:px-8">
      <Card>
        <CardHeader className="space-y-1">
          <CardTitle className="text-2xl">{data.title}</CardTitle>
        </CardHeader>
        <CardContent>
          {tasks.map((todo) => (
            <TodoItem key={todo.id} id={todo.id} removeItem={removeItem} />
          ))}
        </CardContent>
        <CardFooter>
          <div className="flex w-full max-w-sm items-center space-x-2">
            <Input
              type="test"
              placeholder="New Todo Item"
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              onKeyDown={(event) => {
                if (event.key === "Enter") {
                  addItem();
                }
              }}
            />
            <Button type="submit" onClick={addItem}>
              <Plus />
            </Button>
          </div>
        </CardFooter>
      </Card>
    </div>
  );
}
