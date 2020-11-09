<template>
  <v-container fluid>
    <v-row>
      <v-col
        lg="4"
        md="6"
        sm="12"
        v-for="course in courseList"
        :key="course.id"
      >
        <v-card
          class="mx-auto"
          height="400px"
          @click="routingToCource(course.courseName)"
        >
          <v-img :src="course.img" height="200px"></v-img>
          <v-row align="center">
            <v-col style="padding-bottom: 0">
              <v-card-title style="font-size: 1em; height: 120px">
                {{ course.courseName }}
              </v-card-title>
            </v-col>
            <v-col cols="3" class="play-icon">
              <v-btn class="ma-4" outlined fab color="teal">
                <v-icon>mdi-play</v-icon>
              </v-btn>
            </v-col>
          </v-row>

          <div class="mx-2">
            <v-progress-linear
              color="deep-orange"
              height="10"
              value="30"
              striped
            ></v-progress-linear>
          </div>
          <v-card-subtitle>
            진행률
          </v-card-subtitle>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class LectureList extends Vue {
  courseList = [
    {
      id: 1,
      img: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
      courseName: "머신러닝으로 주가 예측하기"
    },
    {
      id: 2,
      img: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
      courseName: "인공지능으로 세상을 바꾸다"
    },
    {
      id: 3,
      img: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
      courseName: "회귀분석을 통한 예측"
    },
    {
      id: 4,
      img: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
      courseName: "텐서플로우 구현하기"
    },
    {
      id: 5,
      img: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
      courseName: "JavaScript 완벽 이해하기"
    },
    {
      id: 6,
      img: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
      courseName: "React 완전 정복"
    },
    {
      id: 7,
      img: "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
      courseName: "Kaggle 데이터로 본 세상"
    }
  ];

  routingToCource(courseName: string) {
    this.$router.push({
      name: "LectureListPage",
      params: { courseName: courseName }
    });
  }
}
</script>

<style>
.play-icon {
  padding: 0px;
}
</style>
