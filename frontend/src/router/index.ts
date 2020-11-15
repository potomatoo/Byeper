import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import StudentPage from "@/views/student/StudentPage.vue";
import LectureListPage from "@/views/student/LectureListPage.vue";
import LecturePage from "@/views/lecture/LecturePage.vue";
import Home from "../views/Home.vue";
import PlayList from "../views/main/PlayList.vue";

// 강사 페이지
import InstructorDashboard from "@/views/lecturer/InstructorDashboard.vue";
import CreateLecture from "@/views/lecturer/CreateLecture.vue";
import InstructorFeedback from "@/views/lecturer/InstructorFeedback.vue";

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
    path: "/course/:playlistTitle/:playlistId",
    name: "LectureListPage",
    component: LectureListPage
  },
  {
    path: "/course/:playlistTitle/:playlistId/lecture/:playId",
    name: "LecturePage",
    component: LecturePage
  },
  {
    path: "/playList/:playListId",
    name: "PlayList",
    component: PlayList
  },
  {
    path: "/instructor",
    name: "InstructorDashboard",
    component: InstructorDashboard
  },
  {
    path: "/instructor/CreateLecture",
    name: "CreateLecture",
    component: CreateLecture
  },
  {
    path: "/instructor/Feedback",
    name: "InstructorFeedback",
    component: InstructorFeedback
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { x: 0, y: 0 };
    }
  }
});

export default router;
