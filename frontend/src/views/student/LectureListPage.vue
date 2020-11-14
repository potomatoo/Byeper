<template>
  <div class="lecture-list-page mx-auto mt-10">
    <div class="lecture-title text-h3 mb-10 ">
      {{ $route.params.playlistTitle }}
    </div>
    <statistics-by-course />
    <lecture-list-by-course />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import StatisticsByCourse from "@/components/dashboard/StatisticsByCourse.vue";
import LectureListByCourse from "@/components/lecture/LectureListByCourse.vue";
import { namespace } from "vuex-class";

const LecturesModule = namespace("LecturesModule");

@Component({
  components: {
    StatisticsByCourse,
    LectureListByCourse
  }
})
export default class LectureListPage extends Vue {
  @LecturesModule.Action FETCH_LECTURE_BY_COURSE: any;

  created() {
    this.FETCH_LECTURE_BY_COURSE(this.$route.params.playlistId);
  }
}
</script>

<style>
.lecture-list-page {
  width: 80%;
}
.lecture-title:before {
  content: "";
  position: absolute;
  background: #121212;
  top: 0;
  left: 0;
  width: 100%;
  height: 300px;
  transform-origin: right;
  animation: revealText 2s ease-in-out forwards;
}
@keyframes revealText {
  0% {
    transform: scaleX(1);
  }
  100% {
    transform: scaleX(0);
  }
}
</style>
