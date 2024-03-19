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

export default function TodoDemo() {
  const [inputValue, setInputValue] = useState("");
  const [tasks, setTasks] = useState([
    {
      id: "1",
      title: "New Test",
      content: null,
      createdOn: null,
      lastUpdatedOn: null,
      util: null,
      complete: false,
    },
    {
      id: "2",
      title: "A sample Todo",
      content: null,
      createdOn: null,
      lastUpdatedOn: null,
      util: null,
      complete: false,
    },
  ]);

  function addItem(): void {
    const newTask = {
      id: tasks.length + 1,
      title: inputValue,
    };
    setTasks([...tasks, newTask]);
    setInputValue("");
  }

  function removeItem(id: string): void {
    setTasks(tasks.filter((task) => task.id !== id));
  }

  return (
    <div className="flex flex-col items-center justify-center lg:px-8">
      <Card>
        <CardHeader className="space-y-1 text-center text-lg">
          <CardTitle className="flex text-2xl items-center">
            CollabTasker
          </CardTitle>
        </CardHeader>
        <CardContent>
          {tasks.map((todo) => (
            <TodoItem
              key={todo.id}
              id={todo.id}
              offlineTitle={todo.title}
              removeItem={removeItem}
              online={false}
            />
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
