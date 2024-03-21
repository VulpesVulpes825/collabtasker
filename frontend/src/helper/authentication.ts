import axios from "axios";
import Cookies from "universal-cookie";
import { jwtDecode } from "jwt-decode";

const API_URL = "/api/v1/auth/";

interface JwtPayload {
  token: string;
}

class AuthService {
  cookies: Cookies = new Cookies();

  async login(username: string, password: string) {
    const response = await axios.post<JwtPayload>(API_URL + "authenticate", {
      username,
      password,
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
      expires: new Date(decoded.exp! * 1000),
    });
  }

  logout() {
    return new Promise(() => {
      this.cookies.remove("jwt_authorization");
    });
  }

  async register(
    username: string,
    firstName: string,
    lastName: string,
    password: string,
  ) {
    const response = await axios.post<JwtPayload>(API_URL + "register", {
      username,
      firstName,
      lastName,
      password,
    });
    console.log(response);
    if (response.data.token) {
      const token: string = response.data.token;
      this.storeJwt(token);
    }
  }

  getCurrentUser() {
    const jwt = this.getJwt();
    if (jwt) {
      const decoded = jwtDecode(jwt);
      return decoded.sub;
    }

    return null;
  }

  getFullName() {
    const jwt = this.getJwt();
    if (jwt) {
      const decoded = jwtDecode(jwt);
      return `${decoded.given_name} ${decoded.family_name}`;
    }

    return null;
  }

  getFirstName() {
    const jwt = this.getJwt();
    if (jwt) {
      const decoded = jwtDecode(jwt);
      return decoded.given_name;
    }

    return null;
  }

  getLastName() {
    const jwt = this.getJwt();
    if (jwt) {
      const decoded = jwtDecode(jwt);
      return decoded.family_name;
    }

    return null;
  }

  getJwt() {
    return this.cookies.get("jwt_authorization");
  }

  isLoggedIn(): boolean {
    const jwt = this.getJwt();
    if (jwt) {
      const expiration = jwtDecode(jwt).exp! * 1000;
      const currentDate = new Date();
      if (expiration < currentDate.getTime()) {
        this.logout()
          .then(() => {
            return false;
          })
          .catch((error) => {
            console.error(error);
            return false;
          });
      } else {
        return true;
      }
    } else {
      return false;
    }
  }
}

export default new AuthService();
