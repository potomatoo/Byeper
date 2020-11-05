<template>
  <v-card class="mx-auto" min-width="344" width="100%" height="250" outlined>
    <v-card-title>최근 학습 강의</v-card-title>
    <v-list-item>
      <v-row class="mb-5">
        <!-- <v-col> -->
        <v-card-text>강의 제목</v-card-text>
        <v-progress-linear
          color="deep-orange"
          height="10"
          value="50"
          striped
        ></v-progress-linear>
        <!-- </v-col>
        <v-col> -->
        <v-card-text>강의 제목</v-card-text>
        <v-progress-linear
          color="deep-orange"
          height="10"
          value="30"
          striped
        ></v-progress-linear>
        <!-- </v-col> -->
      </v-row>
    </v-list-item>
  </v-card>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class LearningVideo extends Vue {}
</script>

<style></style>
