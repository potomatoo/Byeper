import { Axios } from "@/service/axios.service";
import { Module } from "vuex";
import { RootState } from "./index";
import { PlayListModule, PlayList, PlayListVideos } from "./PlayList.interface";

const module: Module<PlayListModule, RootState> = {
  namespaced: true,
  state: {
    AllPlayList: null,
    scrollEnd: false,
    PlayList: null,
    PlayListVideos: null
  },

  getters: {},

  mutations: {
    SET_ALL_PLAYLIST(state, playLists: PlayList[]) {
      if (state.AllPlayList === null) {
        state.AllPlayList = playLists;
      } else if (playLists.length && !state.scrollEnd) {
        state.AllPlayList = state.AllPlayList?.concat(playLists);
      } else if (!playLists.length) {
        state.scrollEnd = true;
      }
    },
    SET_PLAYLIST(state, playList: PlayList) {
      state.PlayList = playList;
    },
    SET_PLAYLIST_VIDEOS(state, playListVideos: PlayListVideos[]) {
      state.PlayListVideos = playListVideos;
    },
    SET_PLAYLIST_ZERO(state) {
      state.AllPlayList = null;
    }
  },

  actions: {
    FETCH_ALL_PLAYLIST({ commit }, { start }: { start: number }) {
      Axios.instance
        .get("/api/public/playlist/teacher", { params: { start } })
        .then(({ data }) => commit("SET_ALL_PLAYLIST", data.data))
        .catch(err => console.error(err));
    },
    FETCH_PLAYLIST({ commit }, { playlistId }: { playlistId: number }) {
      Axios.instance
        .get("/api/public/playlist/detail", { params: { playlistId } })
        .then(({ data }) => commit("SET_PLAYLIST", data.data))
        .catch(err => console.error(err));
    },
    BUY_PLAYLIST({ commit }, { playlistId }: { playlistId: number }) {
      Axios.instance
        .get("/api/private/playlist/subscribe", { params: { playlistId } })
        .catch(err => console.error(err));
    },
    FETCH_PLAYLIST_VIDEOS({ commit }, { playlistId }: { playlistId: number }) {
      Axios.instance
        .get("/api/public/video/findplaylist", { params: { playlistId } })
        .then(({ data }) => commit("SET_PLAYLIST_VIDEOS", data.data))
        .catch(err => console.error(err));
    }
  }
};

export default module;
