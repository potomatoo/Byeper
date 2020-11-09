<template>
  <div
    class="lecture-list-page mx-auto mt-10"
    style="font-family: 'Do Hyeon', sans-serif !important;"
  >
    <div
      class="lecture-title text-h3 mb-10 "
      style="text-align: center; font-family: 'Do Hyeon', sans-serif !important;"
    >
      {{ this.$route.params.courseName }}
    </div>
    <statistics-by-cource />
    <play-list-videos />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import StatisticsByCource from "@/components/dashboard/StatisticsByCource.vue";
import PlayListVideos from "@/components/main/PlayListVideos.vue";

@Component({
  components: {
    StatisticsByCource,
    PlayListVideos
  }
})
export default class LectureListPage extends Vue {}
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
  animation: revealText 3s ease-in-out forwards;
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
