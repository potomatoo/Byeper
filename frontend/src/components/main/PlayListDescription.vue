<template>
  <div>
    <div class="banner">
      <v-row>
        <v-col>
          <div class="videoBx">
            <video
              src="@/assets/mainVideo.mp4"
              muted
              autoplay
              loop
              type="mp4"
            ></video>
          </div>
        </v-col>

        <v-col>
          <div class="textBox">
            <h2>{{ playList.playListName }}</h2>
            <p>{{ playList.playListDescription }}</p>
          </div>
        </v-col>
        <v-col>
          <div class="tableBox">
            <v-simple-table dense max-height="400px" width="400px">
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left">
                      재생번호
                    </th>
                    <th class="text-left">
                      제목
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    v-for="item in play"
                    :key="item.id"
                    @click="moveScroll(item.id)"
                  >
                    <td class="text-center" style="cursor: pointer;">
                      {{ item.id }}
                    </td>
                    <td style="cursor: pointer">{{ item.playName }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>
        </v-col>
      </v-row>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class IntroMain extends Vue {
  $vuetify: any;

  playList = {
    playListName: "슬기로운 싸피 생활",
    playListDescription:
      "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Obcaecatieum voluptate est eius, voluptatibus earum ipsum asperiores modi odit numquam alias. Reiciendis saepe ex, eius animi maxime non debitis! Recusandae!"
  };
  headers = [
    {
      text: "커리큘럼",
      align: "start",
      value: "playName",
      groupable: false
    }
  ];

  play = [
    { id: 1, playName: "머신러닝" },
    { id: 2, playName: "인공지능 개론" },
    { id: 3, playName: "인공신경망 최적화" },
    { id: 4, playName: "IOT 접목하기" },
    { id: 5, playName: "텐서플로우와 코드" },
    { id: 6, playName: "KERAS와 코드" },
    { id: 7, playName: "CNN과 RNN" }
  ];

  moveScroll(scrollId: string) {
    const target: HTMLElement = document.getElementById(
      scrollId
    ) as HTMLElement;
    const scrollLocation: number = target.offsetTop;
    this.$vuetify.goTo(scrollLocation + 500);
  }
}
</script>

<style scoped>
.banner {
  position: relative;
  width: 100%;
  padding: 0 100px;
  display: flex;
  min-height: 500px;
  justify-content: flex-start;
  align-items: center;
}

.banner video {
  position: relative;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  opacity: 0.1;
  pointer-events: none;
}

.banner .textBox {
  position: relative;
  max-width: 500px;
  z-index: 2;
}

.banner .textBox:before {
  content: "";
  position: absolute;
  background: #121212;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  transform-origin: right;
  animation: revealText 2s ease-in-out forwards;
}
@keyframes revealText {
  0% {
    transform: scaleX(1);
  }
  100% {
    transform: scaleX(0);
  }
}

.tableBox {
  margin-left: 50px;
  opacity: 0;
  animation: fadeInBottom 0.5s linear forwards;
  animation-delay: 1.5s;
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
.banner .textBox h2 {
  font-size: 3em;
}

.banner .textBox p {
  font-size: 1.1em;
  margin: 5px 0 0 0;
}

.banner .videoBx {
  position: relative;
  background: #fff;
  margin-top: 30px;
  margin-right: 50px;
  width: 300px;
  height: 200px;
  z-index: 1;
}

.banner .videoBx video {
  opacity: 1;
}
</style>
