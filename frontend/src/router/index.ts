import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import StudentPage from "@/views/student/StudentPage.vue";
import LectureListPage from "@/views/student/LectureListPage.vue";
import LecturePage from "@/views/lecture/LecturePage.vue";
import Home from "../views/Home.vue";
import PlayList from "../views/main/PlayList.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/student",
    name: "StudentPage",
    component: StudentPage
  },
  {
    path: "/course",
    name: "LectureListPage",
    component: LectureListPage
  },
  {
    path: "/course/lecture",
    name: "LecturePage",
    component: LecturePage
  },
  {
    path: "/playList/:playListName",
    name: "PlayList",
    component: PlayList
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
