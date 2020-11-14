<template>
  <youtube
    v-if="lecture"
    :video-id="lecture.video_id"
    fitParent
    ref="youtube"
  ></youtube>
</template>

<script>
import { Component, Vue, Watch } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { Lecture } from "../../store/Lectures.interface";

const LecturesModule = namespace("LecturesModule");

@Component
export default class LectureVideo extends Vue {
  @LecturesModule.State lecture;

  @Watch("lecture")
  emitVideo() {
    if (this.lecture && this.$refs.youtube) {
      this.$emit("player", this.$refs.youtube.player);
    }
  }
}
</script>

<style scoped></style>
