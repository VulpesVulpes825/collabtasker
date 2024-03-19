import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card.tsx";
import AuthService from "@/helper/authentication.ts";
import { useState } from "react";
import { Link, useLoaderData } from "react-router-dom";
import { Button } from "@/components/ui/button.tsx";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import BoardService from "@/helper/board-service.ts";

export default function TodoBoards() {
  const data = useLoaderData();
  const username: string = AuthService.getCurrentUser()!;

  const [boards, setBoards] = useState(data);
  const [boardTitle, setboardTitle] = useState("");

  function addNewBoard() {
    BoardService.setBoard(boardTitle)
      .then((response) => {
        const data = response.data;
        const newBoard = {
          id: data.id,
          title: data.title,
        };
        setBoards([...boards, newBoard]);
        setboardTitle("");
      })
      .catch((error) => {
        console.error(error);
      });
  }

  return (
    <Card className="min-w-96">
      <CardHeader>
        <CardTitle>{username} &apos;s board</CardTitle>
      </CardHeader>
      <CardContent>
        {boards.map((board) => (
          <Card key={board.id}>
            <Link to={`/boards/${board.id}`}>
              <CardHeader>
                <CardTitle>{board.title}</CardTitle>
              </CardHeader>
            </Link>
          </Card>
        ))}
      </CardContent>
      <CardFooter>
        <Dialog>
          <DialogTrigger asChild>
            <Button className="min-w-full">+</Button>
          </DialogTrigger>
          <DialogContent className="sm:max-w-[425px]">
            <DialogHeader>
              <DialogTitle>Create New Todo Board</DialogTitle>
              <DialogDescription>Add a new Todo Board.</DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Label htmlFor="name" className="text-right">
                  Board Title
                </Label>
                <Input
                  id="name"
                  value={boardTitle}
                  className="col-span-3"
                  onChange={(e) => setboardTitle(e.target.value)}
                />
              </div>
            </div>
            <DialogFooter>
              <DialogClose asChild>
                <Button
                  type="submit"
                  onClick={addNewBoard}
                  disabled={!boardTitle}
                >
                  Add board
                </Button>
              </DialogClose>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </CardFooter>
    </Card>
  );
}
