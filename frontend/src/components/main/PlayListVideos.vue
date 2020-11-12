<template>
  <div class="playlistvideos">
    <v-container>
      <v-row
        v-for="video in PlayListVideos"
        :key="video.video_id"
        style="margin: 50px 0 50px 0"
      >
        <v-col cols="6">
          <div class="videodescription" :id="video.video_id">
            <div class="content">
              <div>
                <h3 class="mb-5">
                  {{ video.video_title }}
                </h3>
                <p style="font-weight: light; ">
                  {{ video.video_description }}
                </p>
                <div>
                  <p
                    style="font-size: 0.75em; font-weight: bold !important; margin: 0 0 10px 0"
                  >
                    학습상태 (수강중)
                  </p>
                  <v-progress-linear
                    color="deep-orange"
                    height="18"
                    :value="video.play_log"
                    striped
                    ><strong style="font-size: 0.8em"
                      >{{ video.play_log }}%</strong
                    ></v-progress-linear
                  >
                </div>
              </div>
            </div>
          </div>
        </v-col>

        <v-col cols="1"></v-col>

        <v-col cols="5" justify="center">
          <div class="card">
            <div class="imgBx">
              <img class="bigimg" :src="video.video_img" />
              <img
                class="smallimg"
                src="@/assets/playBtn.png"
                @click="goLecture(video.video_title)"
              />
            </div>
            <div class="content">
              <img :src="video.video_img" />
            </div>
          </div>
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
  font-family: "Do Hyeon", sans-serif;
}
.playlistvideos {
  display: flex;
  justify-content: center;
  align-items: center;
  background: #222;
  min-height: 100vh;
}
.container {
  position: relative;
  width: 1000px;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin: 20px;
}
.container .card {
  position: relative;
  height: 225px;
  display: flex;
  width: 400px;
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

.card .imgBx {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #333;
  z-index: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  transition: 0.5s ease-in-out;
}
.card:hover .imgBx {
  width: 100px;
  height: 100px;
  left: -50px;
  top: calc(50% - 50px);
  transition: 0.5s ease-in-out;
  background: #ff00ff00;
}
.card .smallimg {
  display: none;
}
.card:hover .smallimg {
  display: block;
  cursor: pointer;
}
.card:hover .bigimg {
  display: none;
}
.card .imgBx img {
  max-height: 100%;
  transition: 0.5s ease-in-out;
}

.card .content {
  position: absolute;
  width: calc(100% - 50px);
  height: 100%;
  padding: 0px;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.card .content img {
  width: 400px;
  height: 225px;
}
@keyframes fadeInBottom {
  0% {
    transform: translateY(20px);
    opacity: 0;
  }
  100% {
    transform: translateY();
    opacity: 1;
  }
}
@media (max-width: 992px) {
  .card {
    width: 400px;
  }
}
@media (max-width: 768px) {
  .card {
    max-width: 300px;
    flex-direction: column;
    height: auto;
  }
  .card .imgBx {
    position: relative;
  }
  .card .imgBx,
  .card:hover .imgBx {
    width: 100%;
    height: 200px;
    left: 0;
  }
  .card .imgBx img,
  .card:hover .imgBx img {
    max-width: 100px;
  }
  .card .content {
    position: relative;
    width: 100%;
  }
}
</style>
