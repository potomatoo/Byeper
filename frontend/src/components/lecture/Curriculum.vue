<template>
  <div v-if="lectures.length">
    <div class="list-page">
      <h1>{{ $route.params.playlistTitle }}</h1>
      <div>진도율: {{ completedLecture }}강 / {{ lectures.length }}강</div>
      <v-progress-linear color="deep-orange" height="12" :value="progress">
        <strong style="font-size: 0.8em">{{ progress }}%</strong>
      </v-progress-linear>
    </div>
    <h2 class="cur-box">목차</h2>
    <v-list class="pt-0" two-line>
      <v-list-item-group v-model="selected" active-class="pink--text">
        <template v-for="(item, index) in lectures">
          <v-list-item :key="item.video_id" @click="goToLecture(item.play_id)">
            <template>
              <v-list-item-icon>
                <v-icon v-if="item.play_complete === 0" color="grey lighten-1">
                  mdi-checkbox-marked-circle-outline
                </v-icon>

                <v-icon v-else color="yellow darken-3">
                  mdi-checkbox-marked-circle
                </v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title
                  class="text-h6"
                  v-text="item.video_title"
                ></v-list-item-title>

                <v-list-item-subtitle
                  class="text--secondary"
                  v-text="
                    `진행률 : ${
                      item.play_complete === 1 ? 100 : item.play_log
                    }%`
                  "
                ></v-list-item-subtitle>

                <!-- <v-list-item-subtitle
                  v-text="item.subtitle"
                ></v-list-item-subtitle> -->
              </v-list-item-content>
            </template>
          </v-list-item>

          <v-divider :key="index"></v-divider>
        </template>
      </v-list-item-group>
    </v-list>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { Lecture } from "../../store/Lectures.interface";

const LecturesModule = namespace("LecturesModule");

@Component
export default class Curriculum extends Vue {
  @LecturesModule.State lecture!: Lecture;
  @LecturesModule.State lectures!: Lecture[];
  @LecturesModule.Action FETCH_LECTURE_BY_COURSE: any;

  get completedLecture() {
    return this.lectures.filter(lec => lec.play_complete === 1).length;
  }
  get progress() {
    return Number(
      ((this.completedLecture / this.lectures.length) * 100).toFixed(1)
    );
  }

  selected = 0;
  @Watch("lectures", { immediate: true, deep: true })
  getSelected() {
    this.lectures.forEach((lec, idx) => {
      if (lec.play_id === Number(this.$route.params.playId)) {
        this.selected = idx;
      }
    });
  }

  goToLecture(playId: number) {
    this.$router.push({
      name: "LecturePage",
      params: {
        playlistTitle: this.$route.params.playlistTitle,
        playlistId: this.$route.params.playlistId,
        playId: String(playId)
      },
      query: {
        tab: "list"
      }
    });
  }

  mounted() {
    const editorContainer: HTMLElement = document.querySelector(
      ".editor-container"
    ) as HTMLElement;
    editorContainer.style.backgroundColor = "#1E1E1E";
    editorContainer.style.paddingBottom = "0";
  }
  created() {
    this.FETCH_LECTURE_BY_COURSE(this.$route.params.playlistId);
  }
}
</script>

<style scoped>
.list-page {
  margin: 30px 30px;
  color: white;
}
.cur-box {
  border-top: 2px solid grey;
  border-bottom: 2px solid grey;
  background-color: #757575;
  margin-bottom: 0px;
  line-height: 50px;
  padding: 10px;
}
</style>
