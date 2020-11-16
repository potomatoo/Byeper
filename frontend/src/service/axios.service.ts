import axios, { AxiosInstance } from "axios";

export class Axios {
  static readonly instance: AxiosInstance = axios.create({
    baseURL: "http://k3b108.p.ssafy.io:8080",
    timeout: 10000000000
  });

  static readonly instanceDjango: AxiosInstance = axios.create({
    baseURL: "http://k3b108.p.ssafy.io",
    timeout: 10000000000
  });
}

Axios.instance.defaults.headers.common[
  "Authorization"
] = window.sessionStorage.getItem("jwt-token");

Axios.instanceDjango.defaults.headers.common[
  "Authorization"
] = window.sessionStorage.getItem("jwt-token");
