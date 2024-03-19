import TodoBoards from "@/components/custom/todo-boards.tsx";

export default function Dashboard() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen lg:px-8">
      <TodoBoards />
    </div>
  );
}
