import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card.tsx";
import AuthService from "@/helper/authentication.ts";
import { useState } from "react";
import { Link, useLoaderData } from "react-router-dom";

export default function TodoBoards() {
  const data = useLoaderData();
  const username: string = AuthService.getCurrentUser()!;

  const [boards, setBoards] = useState(data);

  return (
    <Card>
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
    </Card>
  );
}
