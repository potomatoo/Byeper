<template>
  <v-card class="mx-auto" min-width="344" width="100%" height="250" outlined>
    <v-card-title>학습통계</v-card-title>
    <v-list-item>
      <v-row class="text-center">
        <v-col>
          <v-progress-circular
            v-if="totalLectureProgress"
            rotate="-90"
            size="100"
            width="15"
            :value="value"
            color="teal"
            >{{ totalLectureProgress.complete }}</v-progress-circular
          >
          <v-card-text>완료 강의수</v-card-text>
        </v-col>
        <v-col>
          <v-progress-circular
            v-if="totalCourseProgress"
            class="graph-class"
            rotate="-90"
            size="100"
            width="15"
            :value="value2"
            color="red"
            >{{ totalCourseProgress.complete }}</v-progress-circular
          >
          <v-card-text>완료 강좌수</v-card-text>
        </v-col>
      </v-row>
    </v-list-item>
  </v-card>
</template>

<script>
import { Component, Vue, Watch } from "vue-property-decorator";
import { namespace } from "vuex-class";

const LecturesModule = namespace("LecturesModule");

@Component
export default class StatisticsByAll extends Vue {
  @LecturesModule.State totalLectureProgress;
  @LecturesModule.Getter totalCourseProgress;

  value = 0;
  value2 = 0;
  interval = {};
  interval2 = {};

  beforeDestroy() {
    clearInterval(this.interval);
    clearInterval(this.interval2);
  }

  @Watch("totalLectureProgress", { immediate: true })
  totalLectureInterval() {
    if (this.totalLectureProgress) {
      this.interval = setInterval(() => {
        if (
          this.totalLectureProgress.complete === 0 ||
          this.value >=
            (this.totalLectureProgress.complete /
              this.totalLectureProgress.total) *
              100
        ) {
          return;
        }
        this.value += 5;
      }, 80);
    }
  }

  @Watch("totalCourseProgress", { immediate: true })
  totalCourseInterval() {
    if (this.totalCourseProgress) {
      this.interval2 = setInterval(() => {
        if (
          this.totalCourseProgress.complete === 0 ||
          this.value2 >=
            (this.totalCourseProgress.complete /
              this.totalCourseProgress.total) *
              100
        ) {
          return;
        }
        this.value2 += 5;
      }, 80);
    }
  }
}
</script>

<style scoped></style>
