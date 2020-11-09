import { Module } from "vuex";
import { RootState } from "./index";
import { AccountsModule } from "./Accounts.interface";
import { Axios } from "@/service/axios.service";
import router from "@/router";

const module: Module<AccountsModule, RootState> = {
  namespaced: true,
  state: {
    user: null,
    token: null || window.sessionStorage.getItem("jwt-token"),
    isSidebar: false
  },

  getters: {
    isLoggedIn(state) {
      return !!state.token;
    },
    userName(state) {
      return state.user?.userName || "guest";
    }
  },

  mutations: {
    SET_TOKEN(state, token) {
      state.token = token;
      window.sessionStorage.setItem("jwt-token", token);
      Axios.instance.defaults.headers.common["Authorization"] = token;
      Axios.instanceRec.defaults.headers.common["Authorization"] = token;
    },
    REMOVE_TOKEN(state) {
      state.token = null;
      window.sessionStorage.removeItem("jwt-token");
    },
    TOGGLE_SIDEBAR(state) {
      state.isSidebar = !state.isSidebar;
    },
    FALSE_SIDEBAR(state) {
      state.isSidebar = false;
    },
    SET_USER_INFO(state, userInfo) {
      state.user = userInfo;
    }
  },

  actions: {
    GOOGLE_LOGIN({ commit }, authToken) {
      console.log(authToken);
      Axios.instance
        .post("/api/public/google/login", authToken)
        .then(({ data }) => {
          commit("SET_TOKEN", data.data.userPassword);
          commit("SET_USER_INFO", data.data);
          router.go(-1);
        })
        .catch(err => console.error(err));
    },
    LOGOUT({ commit }) {
      Axios.instance
        .get("/api/public/logout")
        .then(res => console.log(res))
        .catch(err => console.error(err));
    }
  }
};

export default module;
