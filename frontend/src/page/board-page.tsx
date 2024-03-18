import { useLoaderData } from "react-router-dom";
import TodoItem from "../component/todo-item.tsx";

export default function BoardPage() {
  const data = useLoaderData();

  return (
    <div className="flex flex-col items-center justify-center min-h-screen lg:px-8">
      <h1>{data.title}</h1>
      {data.items.map((todo) => (
        <TodoItem key={todo.id} id={todo.id} />
      ))}
    </div>
  );
}
