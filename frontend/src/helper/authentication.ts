import axios from "axios";
import Cookies from "universal-cookie";
import { jwtDecode } from "jwt-decode";

const API_URL = "http://localhost:5173/api/v1/auth/";

class AuthService {

  cookies: Cookies = new Cookies();

  async login(username: string, password: string) {
    const response = await axios
      .post(API_URL + "authenticate", {
        username,
        password
      });
    console.log(response);
    if (response.data.token) {
      const token: string = response.data.token;
      this.storeJwt(token);
    }
  }

  storeJwt(token: string) {
    const decoded = jwtDecode(token);
    console.log(decoded);
    this.cookies.set("jwt_authorization", token, {
      expires: new Date(decoded.exp * 1000)
    });
  }


  logout() {
    this.cookies.remove("jwt_authorization");
  }

  async register(username: string, firstName: string, lastName: string, password: string) {
    const response = await axios.post(API_URL + "register", {
      username,
      firstName,
      lastName,
      password
    });
    console.log(response);
    if (response.data.token) {
      const token: string = response.data.token;
      this.storeJwt(token);
    }
  }

  getCurrentUser() {
    const userStr = this.cookies.get("jwt_authorization");
    if (userStr) return userStr;

    return null;
  }

  isLoggedIn() {
    return this.cookies.get("jwt_authorization") !== undefined;
  }
}

export default new AuthService();