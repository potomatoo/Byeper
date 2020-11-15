import { Module } from "vuex";
import { RootState } from "./index";
import { LecturesModule, Progress, ProblemList } from "./Lectures.interface";
import { Axios } from "@/service/axios.service";

const module: Module<LecturesModule, RootState> = {
  namespaced: true,
  state: {
    lectures: [],
    allCaptureImgs: [],
    courses: [],
    lecture: null,
    totalLectureProgress: null,
    courseProgress: [],
    ProblemList: []
  },

  getters: {
    totalCourseProgress(state) {
      const complete = state.courseProgress.filter(
        course => course.total === course.complete
      ).length;
      return { total: state.courseProgress.length, complete };
    }
  },

  mutations: {
    SET_ALL_CAPTURE_IMAGES(state, imgs) {
      state.allCaptureImgs = imgs;
    },
    SET_COURSE_BY_STUDENT(state, courses) {
      state.courses = courses;
    },
    SET_LECTURE_BY_COURSE(state, lectures) {
      state.lectures = lectures;
    },
    SET_LECTURE(state, lecture) {
      state.lecture = lecture;
    },
    SET_PLAYLIST_PROGRESS(state, progress) {
      const arrProgress: Progress[] = [];
      Object.keys(progress).forEach(el => {
        const obj: Progress = { id: "0", total: 0, complete: 0 };
        obj.id = el;
        obj.total = progress[el].total;
        obj.complete = progress[el].complete;
        el === "0" ? (state.totalLectureProgress = obj) : arrProgress.push(obj);
      });
      state.courseProgress = arrProgress;
    },
    SET_PROBLEM_LIST(state, ProblemList: ProblemList[]) {
      state.ProblemList = ProblemList;
    }
  },

  actions: {
    FETCH_CAPTURE_IMAGE(_, { id, time }) {
      return Axios.instanceDjango
        .post("/api/django/summary/extract_time/", { id, time })
        .then(({ data }) => data)
        .catch(err => console.error(err));
    },
    FETCH_ALL_CAPTURE_IMAGES({ commit }, videoId) {
      // Axios.instanceDjango
      //   .get(`/file/${videoId}/${videoId}.json`)
      //   .then(res => console.log(res))
      //   .catch(err => console.error(err));
      const xhr = new XMLHttpRequest();
      xhr.addEventListener("load", function() {
        commit("SET_ALL_CAPTURE_IMAGES", JSON.parse(this.responseText));
      });
      xhr.open(
        "GET",
        `http://k3b108.p.ssafy.io/file/${videoId}/${videoId}.json`
      );
      xhr.send();
    },
    FETCH_COURSE_BY_STUDENT({ commit }) {
      Axios.instance
        .get("/api/public/playlist/user")
        .then(({ data }) => commit("SET_COURSE_BY_STUDENT", data.data))
        .catch(err => console.error(err));
    },
    FETCH_LECTURE_BY_COURSE({ commit }, playlistId) {
      Axios.instance
        .get("/api/public/video/findplaylist", { params: { playlistId } })
        .then(({ data }) => commit("SET_LECTURE_BY_COURSE", data.data))
        .catch(err => console.error(err));
    },
    FETCH_LECTURE_DETAIL({ commit }, playId) {
      Axios.instance
        .get("/api/public/play/detail", { params: { playId } })
        .then(({ data }) => commit("SET_LECTURE", data.data))
        .catch(err => console.error(err));
    },
    FETCH_PLAYLIST_PROGRESS({ commit }) {
      Axios.instance
        .get("/api/public/playlist/progress")
        .then(({ data }) => commit("SET_PLAYLIST_PROGRESS", data.data))
        .catch(err => console.error(err));
    },
    FETCH_PROBLEM_LIST({ commit }, videoId) {
      Axios.instance
        .get("/api/public/problem/list", { params: { videoId } })
        .then(({ data }) => commit("SET_PROBLEM_LIST", data.data))
        .catch(err => console.error(err));
    }
  }
};

export default module;
