<template>
  <div class="playlistvideos">
    <v-container>
      <v-row
        v-for="video in PlayListVideos"
        :key="video.play_id"
        style="margin: 50px 0 50px 0"
      >
        <v-col cols="6">
          <div class="videodescription" :id="video.play_id">
            <div class="content">
              <div>
                <h3 class="mb-5">
                  {{ video.video_title }}
                </h3>
                <p style="font-weight: light; ">
                  {{ video.video_description }}
                </p>
              </div>
            </div>
          </div>
        </v-col>

        <v-col cols="1"></v-col>

        <v-col cols="5" justify="center">
          <iframe
            :src="`https://youtube.com/embed/${video.video_id}`"
            width="400px"
            height="225px"
            frameborder="0"
          />
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { PlayList } from "../../store/PlayList.interface";

const PlayListModule = namespace("PlayListModule");

@Component
export default class PlayListRoadmap extends Vue {
  @PlayListModule.State PlayListVideos!: [];

  goLecture(courseName: string) {
    this.$router.push({
      name: "LecturePage",
      params: { courseName: courseName }
    });
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.playlistvideos {
  display: flex;
  justify-content: center;
  align-items: center;
  background: #222;
}
.container {
  position: relative;
  width: 1000px;
  left: 0;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin: 20px;
}

.videodescription {
  position: relative;
  height: 250px;
  display: flex;
  width: 500px;
}

.videodescription .content {
  position: absolute;
  display: flex;
  width: calc(100% - 75px);
  height: 100%;
  justify-content: center;
  align-items: center;
}
.videodescription .content h3 {
  margin-bottom: 5px;
  font-size: 24px;
}
</style>
