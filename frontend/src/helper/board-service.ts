import AuthService from "./authentication.ts";
import axios, { AxiosRequestConfig } from "axios";

const API_URL = "http://localhost:5173/api/v1/boards/";

class BoardService {
  jwt: string = AuthService.getJwt();

  config: AxiosRequestConfig = {
    headers: {
      Authorization: `Bearer ${this.jwt}`,
    },
  };

  async getBoard(index: number) {
    const response = await axios.get(API_URL + index, this.config);
    console.log(response);
    console.log(response.data);
    return response.data;
  }
}

export default new BoardService();
