import { Space } from "antd";
import TodoItem from "./todo-item.tsx";

export default function TodoBoard() {
  return (
    <Space direction="vertical" size={16}>
      <TodoItem></TodoItem>
    </Space>
  );
}
