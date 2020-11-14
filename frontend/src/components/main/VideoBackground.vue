<template>
  <div>
    <div class="banner">
      <video
        src="@/assets/mainVideo.mp4"
        muted
        autoplay
        loop
        type="mp4"
      ></video>
      <div class="textBox">
        <h2>VPT</h2>
        <p>
          Video Summary 서비스를 이용하여 동영상을 학습하고 자동으로 PPT를
          추출해보세요!
        </p>
        <a @click="moveScroll">영상 담기</a>
      </div>
      <div class="videoBx">
        <video
          src="@/assets/mainVideo.mp4"
          muted
          autoplay
          loop
          type="mp4"
        ></video>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class IntroMain extends Vue {
  $vuetify: any;

  moveScroll() {
    this.$vuetify.goTo(530);
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
  justify-content: space-around;
  align-items: center;
}
.banner:before {
  content: "";
  position: absolute;
  right: 0;
  top: 0;
  width: 400px;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
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
  max-width: 400px;
  margin-right: 100px;
  z-index: 2;
}

.banner .textBox h2 {
  font-size: 2em;
}
.banner .textBox p {
  font-size: 1.1em;
  margin: 5px 0 25px;
}
.banner .textBox a {
  position: relative;
  display: inline-block;
  background: #fff;
  color: #111;
  padding: 15px 25px;
  text-decoration: none;
  font-size: 1.1em;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: bold;
  border-radius: 10px;
}
.banner .videoBx {
  position: relative;
  background: #fff;
  margin-left: 40px;
  width: 500px;
  height: 300px;
  z-index: 1;
}

.banner .videoBx video {
  opacity: 1;
}
</style>
