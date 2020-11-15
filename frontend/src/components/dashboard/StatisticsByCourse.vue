<template>
  <v-card class="mx-auto" min-width="344" width="100%" height="250" outlined>
    <v-card-title style="margin-left: 20px;">내 학습상황</v-card-title>
    <v-list-item>
      <v-row>
        <v-col style="text-align: center">
          <v-card-title style="font-size: 1.5em; display: block"
            >13/56</v-card-title
          >
          <v-card-text style="font-size: 1em;">완료 강의수</v-card-text>
        </v-col>
        <v-col style="text-align: center">
          <v-card-title style="font-size: 1.5em; display: block"
            >3h 56m</v-card-title
          >
          <v-card-text style="font-size: 1em;">총 학습 시간</v-card-text>
        </v-col>
        <v-col style="text-align: center">
          <v-progress-circular
            rotate="-90"
            size="100"
            width="15"
            :value="value"
            color="red"
            >{{ value }}</v-progress-circular
          >
          <v-card-text style="font-size: 1em;">학습률</v-card-text>
        </v-col>
      </v-row>
    </v-list-item>
  </v-card>
</template>

<script>
import { Component, Vue } from "vue-property-decorator";

@Component
export default class StatisticsByCourse extends Vue {
  value = 0;
  queryAndIndeterminate() {
    setTimeout(() => {
      this.query = false;

      this.interval = setInterval(() => {
        if (this.value === 60) {
          return;
        }
        this.value += 5;
      }, 150);
    });
  }

  mounted() {
    this.queryAndIndeterminate();
  }
}
</script>

<style></style>
