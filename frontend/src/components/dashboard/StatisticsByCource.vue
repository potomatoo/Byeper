<template>
  <v-card class="mx-auto" min-width="344" width="100%" height="250" outlined>
    <v-card-title>내 학습상황</v-card-title>
    <v-list-item>
      <v-row>
        <v-col>
          <v-card-title>13/56</v-card-title>
          <v-card-text>완료 강의수</v-card-text>
        </v-col>
        <v-col>
          <v-card-title>3h 56m</v-card-title>
          <v-card-text>총 학습 시간</v-card-text>
        </v-col>
        <v-col>
          <v-progress-circular
            rotate="270"
            size="100"
            width="15"
            value="30"
            color="red"
            >30</v-progress-circular
          >
          <v-card-text>학습률</v-card-text>
        </v-col>
      </v-row>
    </v-list-item>
  </v-card>
</template>

<script>
import { Component, Vue } from "vue-property-decorator";

@Component
export default class StatisticsByCource extends Vue {}
</script>

<style></style>
