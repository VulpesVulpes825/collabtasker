import AuthService from "./authentication.ts";
import axios, { AxiosRequestConfig } from "axios";

const API_URL = "/api/v1/boards/";

class BoardService {
  jwt: string = AuthService.getJwt();

  config: AxiosRequestConfig = {
    headers: {
      Authorization: `Bearer ${this.jwt}`,
    },
  };

  async getBoard(index: string | undefined) {
    const response = await axios.get(API_URL + index, this.config);
    console.log(response.data);
    return response.data;
  }

  async getBoards() {
    const response = await axios.get(API_URL, this.config);
    console.log(response.data);
    return response.data;
  }

  async setItem(boardId: number, title: string) {
    return await axios.post(
      API_URL + boardId + "/item",
      {
        title,
      },
      this.config,
    );
  }

  async setBoard(title: string) {
    return await axios.post(
      API_URL,
      {
        title,
      },
      this.config,
    );
  }
}

export default new BoardService();
