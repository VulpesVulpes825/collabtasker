import AuthService from "./authentication.ts";
import axios, { AxiosRequestConfig } from "axios";

const API_URL = "/api/v1/items/";

interface ItemPayload {
  id: string;
  title: string;
  content: string;
  until: Date | undefined;
  complete: boolean;
}

class ItemService {
  jwt: string = AuthService.getJwt();

  config: AxiosRequestConfig = {
    headers: {
      Authorization: `Bearer ${this.jwt}`,
    },
  };

  async getItem(index: string) {
    const response = await axios.get(API_URL + index, this.config);
    console.log(response);
    return response;
  }

  async setItem(
    id: string,
    title: string,
    content: string,
    until: Date | null,
    complete: boolean,
  ) {
    const payload = {
      id,
      title,
      content,
      complete,
      until: null,
    };
    if (typeof until !== "undefined") {
      payload.until = until;
    }
    const response = await axios.post<ItemPayload>(
      API_URL,
      {
        id,
        title,
        content,
        complete,
        until,
      },
      this.config,
    );
    console.log(response);
    console.log(response.data);
    return response.data;
  }

  async deleteItem(id: string) {
    return await axios.delete(API_URL + id, this.config);
  }
}

export default new ItemService();
