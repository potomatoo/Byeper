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
      Axios.instanceDjango.defaults.headers.common["Authorization"] = token;
    },
    REMOVE_TOKEN(state) {
      state.token = null;
      window.sessionStorage.removeItem("jwt-token");
      Axios.instance.defaults.headers.common["Authorization"] = null;
      Axios.instanceDjango.defaults.headers.common["Authorization"] = null;
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
      Axios.instance
        .post("/api/public/google/login", authToken)
        .then(({ data }) => {
          console.log(data);
          commit("SET_TOKEN", data.data.userPassword);
          commit("SET_USER_INFO", data.data);
        })
        .catch(err => console.error(err));
    },
    LOGOUT({ commit }) {
      Axios.instance
        .get("/api/public/logout")
        .then(res => commit("REMOVE_TOKEN"))
        .catch(err => console.error(err));
    },
    FETCH_USER_INFO({ commit }) {
      Axios.instance
        .get("/api/private/user/detail")
        .then(({ data }) => commit("SET_USER_INFO", data.data))
        .catch(err => console.error(err));
    },
    CHANGE_USER_NAME({ dispatch }, userName) {
      Axios.instance
        .put("/api/private/user/changeusername", { userName })
        .then(({ data }) => {
          if (data.status) {
            dispatch("FETCH_USER_INFO");
            alert("수정이 완료되었습니다.");
          }
        })
        .catch(err => console.error(err));
    },
    REGISTER_TEACHER({ dispatch }) {
      Axios.instance
        .put("/api/private/user/registteacher")
        .then(({ data }) => {
          if (data.status) {
            dispatch("FETCH_USER_INFO");
            alert("강사등록이 완료되었습니다.");
          }
        })
        .catch(err => console.error(err));
    }
  }
};

export default module;
