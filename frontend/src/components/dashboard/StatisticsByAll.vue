<template>
  <v-card class="mx-auto" min-width="344" width="100%" height="250" outlined>
    <v-card-title>학습통계</v-card-title>
    <v-list-item>
      <v-row>
        <v-col>
          <v-progress-circular
            rotate="-90"
            size="100"
            width="15"
            :value="value"
            color="teal"
            >{{ value }}</v-progress-circular
          >
          <v-card-text>완료 강의수</v-card-text>
        </v-col>
        <v-col>
          <v-progress-circular
            rotate="-90"
            size="100"
            width="15"
            :value="value2"
            color="red"
            >{{ value2 }}</v-progress-circular
          >
          <v-card-text>완료 수업수</v-card-text>
        </v-col>
      </v-row>
    </v-list-item>
  </v-card>
</template>

<script>
import { Component, Vue } from "vue-property-decorator";

@Component
export default class StatisticsByAll extends Vue {
  value = 0;
  value2 = 0;
  interval = {};
  interval2 = {};

  beforeDestroy() {
    clearInterval(this.interval);
    clearInterval(this.interval2);
  }

  mounted() {
    (this.interval = setInterval(() => {
      if (this.value === 60) {
        return;
      }
      this.value += 10;
    }, 150))(
      (this.interval2 = setInterval(() => {
        if (this.value2 === 30) {
          return;
        }
        this.value2 += 10;
      }, 150))
    );
  }
}
</script>

<style></style>
