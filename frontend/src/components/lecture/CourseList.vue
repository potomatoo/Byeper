<template>
  <v-container fluid>
    <v-row>
      <v-col
        lg="4"
        md="6"
        sm="12"
        v-for="course in courses"
        :key="course.playlistId"
      >
        <v-card
          class="mx-auto"
          height="400px"
          @click="routingToCourse(course.playlistId, course.playlistTitle)"
        >
          <v-img :src="course.playlistImg" height="200px"></v-img>
          <v-row align="center">
            <v-col style="padding-bottom: 0">
              <v-card-title style="font-size: 1em; height: 120px">
                {{ course.playlistTitle }}
              </v-card-title>
            </v-col>
            <v-col cols="3" class="play-icon">
              <v-btn class="ma-4" outlined fab color="teal">
                <v-icon>mdi-play</v-icon>
              </v-btn>
            </v-col>
          </v-row>
          <div class="mt-4">
            <v-progress-linear
              color="deep-orange"
              height="15"
              :value="computedProgressByCourse[course.playlistId]"
              striped
            >
              <div class="text-caption">
                {{ computedProgressByCourse[course.playlistId] + "%" }}
              </div>
            </v-progress-linear>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { Course } from "../../store/Lectures.interface";

const LecturesModule = namespace("LecturesModule");
@Component
export default class CourseList extends Vue {
  @LecturesModule.State courses!: Course[];
  @LecturesModule.State lectureProgress!: {};
  @LecturesModule.Getter computedProgressByCourse!: {};

  routingToCourse(playlistId: number, playlistTitle: string) {
    this.$router.push({
      name: "LectureListPage",
      params: { playlistId: String(playlistId), playlistTitle }
    });
  }
}
</script>

<style>
.play-icon {
  padding: 0px;
}
</style>
