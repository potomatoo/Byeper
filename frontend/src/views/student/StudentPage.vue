<template>
  <div class="student-page mx-auto mt-10">
    <div class="text-h3 my-5">
      학생페이지
    </div>
    <v-row>
      <v-col>
        <profile />
        <statistics-by-all />
      </v-col>
      <v-col>
        <finished-lecture />
        <learning-video />
      </v-col>
    </v-row>
    <div class="text-h5 my-5">
      전체 재생 목록
    </div>
    <course-list class="mb-10" />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Profile from "@/components/dashboard/Profile.vue";
import StatisticsByAll from "@/components/dashboard/StatisticsByAll.vue";
import LearningVideo from "@/components/dashboard/LearningVideo.vue";
import FinishedLecture from "@/components/dashboard/FinishedLecture.vue";
import CourseList from "@/components/lecture/CourseList.vue";
import { namespace } from "vuex-class";

const LecturesModule = namespace("LecturesModule");

@Component({
  components: {
    Profile,
    StatisticsByAll,
    LearningVideo,
    FinishedLecture,
    CourseList
  }
})
export default class StudentPage extends Vue {
  @LecturesModule.Action FETCH_COURSE_BY_STUDENT: any;
  @LecturesModule.Action FETCH_PLAYLIST_PROGRESS: any;

  created() {
    this.FETCH_COURSE_BY_STUDENT();
    this.FETCH_PLAYLIST_PROGRESS();
  }
}
</script>

<style scoped>
.student-page {
  width: 80%;
}
</style>
