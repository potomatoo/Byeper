<template>
  <youtube
    v-if="lecture"
    :video-id="lecture.video_id"
    fitParent
    :player-vars="playerVars"
    @playing="playing"
    ref="youtube"
  ></youtube>
</template>

<script>
import { Component, Vue, Watch } from "vue-property-decorator";
import { namespace } from "vuex-class";

const LecturesModule = namespace("LecturesModule");

@Component
export default class LectureVideo extends Vue {
  @LecturesModule.State lecture;
  @LecturesModule.Action UPDATE_LECTURE_INFO;

  playerVars = {
    start: 15
  };

  runningTime = 0;

  interval = null;

  @Watch("lecture", { immediate: true, deep: true })
  emitVideo() {
    if (this.lecture) {
      setTimeout(() => {
        if (this.$refs.youtube) {
          this.$emit("player", this.$refs.youtube.player);
        }
      }, 50);
    }
  }

  async setRunningTime() {
    this.runningTime = await this.$refs.youtube.player.getDuration();
  }

  updateCompleteState() {
    const playInfo = { ...this.lecture };
    playInfo.play_complete = 1;
    this.UPDATE_LECTURE_INFO(playInfo);
  }

  async checkComplete() {
    const currentTime = await this.$refs.youtube.player.getCurrentTime();
    const progress = Number(((currentTime / this.runningTime) * 100).toFixed());
    if (progress >= 80) {
      this.updateCompleteState();
      clearInterval(this.interval);
    } else if (this.lecture.play_complete === 1) {
      clearInterval(this.interval);
    }
  }
  playing() {
    this.setRunningTime();
    if (this.interval) {
      clearInterval(this.interval);
    }
    if (this.lecture.play_complete === 0) {
      this.interval = setInterval(this.checkComplete, 2000);
    } else {
      clearInterval(this.interval);
    }
  }

  beforeDestroy() {
    clearInterval(this.interval);
    this.checkComplete();
  }
}
</script>

<style scoped></style>
