<template>
  <div class="full-height">
    <div>
      <video-background />
    </div>
    <v-divider
      class="light"
      style="font-weight: bold; margin-top: 30px; "
    ></v-divider>

    <all-play-list />
  </div>
</template>

<script>
import { Component, Vue } from "vue-property-decorator";

import AllPlayList from "@/components/main/AllPlayList.vue";
import VideoBackground from "@/components/main/VideoBackground.vue";

@Component({
  components: {
    AllPlayList,
    VideoBackground
  }
})
export default class Home extends Vue {
  myValue = 0;
  mounted() {
    this.myValue = 170;
  }
}
</script>

<style scoped>
.full-height {
  height: 100%;
}
</style>
