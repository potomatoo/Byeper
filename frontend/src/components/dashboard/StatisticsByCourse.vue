<template>
  <v-card class="mx-auto" min-width="344" width="100%" height="250" outlined>
    <v-card-title style="margin-left: 20px;">내 학습상황</v-card-title>
    <v-list-item>
      <v-row>
        <v-col style="text-align: center">
          <v-card-title style="font-size: 1.5em; display: block">{{
            completedLectureCnt + " / " + lectureCnt
          }}</v-card-title>
          <v-card-text style="font-size: 1em;">완료 강의수</v-card-text>
        </v-col>
        <!-- <v-col style="text-align: center">
          <v-card-title style="font-size: 1.5em; display: block"
            >3h 56m</v-card-title
          >
          <v-card-text style="font-size: 1em;">총 학습 시간</v-card-text>
        </v-col> -->
        <v-col style="text-align: center">
          <v-progress-circular
            rotate="-90"
            size="100"
            width="15"
            :value="value"
            color="red"
            >{{ computedProgressByCourse[$route.params.playlistId] }}
          </v-progress-circular>
          <v-card-text style="font-size: 1em;">학습률</v-card-text>
        </v-col>
      </v-row>
    </v-list-item>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { Lecture } from "../../store/Lectures.interface";

const LecturesModule = namespace("LecturesModule");

@Component
export default class StatisticsByCourse extends Vue {
  @LecturesModule.State lectures!: Lecture[];
  @LecturesModule.Getter computedProgressByCourse!: { [key: string]: any };

  lectureCnt = 0;

  completedLectureCnt = 0;

  value = 0;

  @Watch("computedProgressByCourse", { immediate: true })
  queryAndIndeterminate() {
    if (Object.keys(this.computedProgressByCourse).length) {
      setInterval(() => {
        if (
          this.value >=
          this.computedProgressByCourse[this.$route.params.playlistId]
        ) {
          return;
        }
        this.value += 5;
      }, 80);
    }
  }

  @Watch("lectures")
  totalLength() {
    if (this.lectures.length) {
      this.lectureCnt = this.lectures.length;
    }
  }

  @Watch("lectures")
  countCompletedLectures() {
    if (this.lectures.length) {
      let cnt = 0;
      this.lectures.forEach(lecture => {
        if (lecture.play_complete !== 0) {
          cnt++;
        }
      });
      this.completedLectureCnt = cnt;
    }
  }
}
</script>

<style></style>
