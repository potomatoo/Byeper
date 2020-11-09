import axios, { AxiosInstance } from "axios";

export class LocalAxios {
  static readonly instance: AxiosInstance = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 5000
  });
}

export class Axios {
  static readonly instance: AxiosInstance = axios.create({
    baseURL: "http://k3b108.p.ssafy.io",
    timeout: 10000
  });

  static readonly instanceRec: AxiosInstance = axios.create({
    baseURL: "http://k3b108.p.ssafy.io",
    timeout: 10000
  });
}

Axios.instance.defaults.headers.common[
  "Authorization"
] = window.sessionStorage.getItem("jwt-token");

Axios.instanceRec.defaults.headers.common[
  "Authorization"
] = window.sessionStorage.getItem("jwt-token");
