<template>
  <v-card class="mx-auto" min-width="344" width="100%" height="250" outlined>
    <v-card-title>최근 학습 강의</v-card-title>
    <v-list-item>
      <v-row class="mb-5">
        <!-- <v-col> -->
        <v-card-text>강의 제목</v-card-text>
        <v-progress-linear
          color="deep-orange"
          height="18"
          :value="value2"
          :active="show2"
          :indeterminate="query2"
          ><strong style="font-size: 0.8em"
            >{{ Math.ceil(value2) }}%</strong
          ></v-progress-linear
        >
        <!-- </v-col>
        <v-col> -->
        <v-card-text>강의 제목</v-card-text>
        <v-progress-linear
          color="deep-orange"
          height="18"
          :value="value"
          :active="show"
          :indeterminate="query"
          ><strong style="font-size: 0.8em"
            >{{ Math.ceil(value) }}%</strong
          ></v-progress-linear
        >
        <!-- </v-col> -->
      </v-row>
    </v-list-item>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class LearningVideo extends Vue {
  value = 0;
  query = false;
  show = true;
  interval = 0;

  value2 = 0;
  query2 = false;
  show2 = true;
  interval2 = 0;

  queryAndIndeterminate() {
    this.query = true;
    this.show = true;
    this.value = 0;
    this.query2 = true;
    this.show2 = true;
    this.value2 = 0;

    setTimeout(() => {
      this.query = false;

      this.interval = setInterval(() => {
        if (this.value === 40) {
          clearInterval(this.interval);
          return;
        }
        this.value += 10;
      }, 50);
    }, 2000);

    setTimeout(() => {
      this.query2 = false;

      this.interval2 = setInterval(() => {
        if (this.value2 === 80) {
          clearInterval(this.interval2);
          return;
        }
        this.value2 += 10;
      }, 50);
    }, 2000);
  }

  mounted() {
    this.queryAndIndeterminate();
  }

  beforeDestroy() {
    clearInterval(this.interval);
  }
}
</script>

<style></style>
