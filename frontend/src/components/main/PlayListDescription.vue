<template>
  <div>
    <div class="banner">
      <div class="videoBx">
        <video
          src="@/assets/mainVideo.mp4"
          muted
          autoplay
          loop
          type="mp4"
        ></video>
      </div>

      <div class="textBox">
        <h2>{{ playList.playListName }}</h2>
        <p>{{ playList.playListDescription }}</p>
      </div>
      <div class="tableBox">
        <v-data-table
          :headers="headers"
          :items="play"
          item-key="id"
          group-by="Section"
          dense
          disable-sort
          hide-default-footer
          style="font-family: 'Noto Sans KR', sans-serif; cursor: pointer"
          @click:row="moveScroll"
        ></v-data-table>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class IntroMain extends Vue {
  $vuetify: any;

  playList = {
    playListName: this.$route.params.playListName,
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
    { id: 1, playName: "머신러닝", Section: "기초다지기" },
    { id: 2, playName: "인공지능 개론", Section: "기초다지기" },
    { id: 3, playName: "인공신경망 최적화", Section: "실습하기" },
    { id: 4, playName: "IOT 접목하기", Section: "실습하기" },
    { id: 5, playName: "텐서플로우와 코드", Section: "실습하기" },
    { id: 6, playName: "KERAS와 코드", Section: "심화학습" },
    { id: 7, playName: "CNN과 RNN", Section: "심화학습" }
  ];

  moveScroll(value: any) {
    const numberScroll: number = value.id;
    const scrollId: string = numberScroll.toString();
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
  position: absolute;
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
  margin-left: 80px;
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
  margin-right: 80px;
  width: 300px;
  height: 200px;
  z-index: 1;
}

.banner .videoBx video {
  opacity: 1;
}
</style>
