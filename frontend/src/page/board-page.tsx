import { useLoaderData } from "react-router-dom";
import TodoItem from "../component/todo-item.tsx";

export default function BoardPage() {
  const data = useLoaderData();

  return (
    <>
      <h1>{data.title}</h1>
      {data.items.map((todo) => (
        <TodoItem title={todo.title} key={todo.id} />
      ))}
    </>
  );
}
