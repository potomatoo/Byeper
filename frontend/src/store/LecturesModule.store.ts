import { Module } from "vuex";
import { RootState } from "./index";
import { LecturesModule } from "./Lectures.interface";
import { Axios } from "@/service/axios.service";

const module: Module<LecturesModule, RootState> = {
  namespaced: true,
  state: {},

  getters: {},

  mutations: {},

  actions: {
    FETCH_CAPTURE_IMAGE(_, { id, time }) {
      Axios.instanceDjango
        .post("/api/django/summary/extract_time/", { id, time })
        .then(res => console.log(res))
        .catch(err => console.error(err));
    }
  }
};

export default module;
