<template>
  <v-container fluid>
    <v-row>
      <v-col cols="4" v-for="(v, i) in img" :key="i">
        <v-card height="350" @click="routingToCource">
          <v-img :src="v" height="200px"></v-img>
          <v-row>
            <v-col>
              <v-card-title> 강의 제목 </v-card-title>
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
  img = [
    "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
    "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
    "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
    "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg",
    "https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
  ];

  routingToCource() {
    this.$router.push({ name: "LectureListPage" });
  }
}
</script>

<style>
.play-icon {
  padding: 0px;
}
</style>
