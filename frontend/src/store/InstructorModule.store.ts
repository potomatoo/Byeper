import { Module } from "vuex";
import { RootState } from ".";
import { InstructorModule } from "./Instructor.interface";
import { Axios } from "@/service/axios.service";

const module: Module<InstructorModule, RootState> = {
  namespaced: true,
  state: {
    playListId: null,
    isLoading: false
  },

  mutations: {
    SET_PLAYLIST_ID(state, payload: number) {
      state.playListId = payload;
    },
    SET_LOADING_TRUE(state) {
      state.isLoading = true;
    },
    SET_LOADING_FALSE(state) {
      state.isLoading = false;
    }
  },

  actions: {
    CREATE_PLAYLIST({ commit, dispatch }, playlist) {
      Axios.instance
        .post("/api/private/playlist/save", playlist, {
          headers: {
            "Content-Type": "application/json"
          }
        })
        .then(({ data }) => {
          commit("SET_PLAYLIST_ID", data.data.playlistId);
        })
        .catch(e => console.error(e));
    },
    ADD_VIDEO({ state }, Video) {
      Axios.instance
        .post(
          "/api/pirvate/playlist/addvideo",
          {
            playlistId: state.playListId,
            videos: Video
          },
          {
            headers: {
              "Content-Type": "application/json"
            }
          }
        )
        .then(res => console.log("add video: "))
        .catch(e => console.error(e));
    }
  }
};

export default module;
