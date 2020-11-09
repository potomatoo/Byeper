<template>
  <div class="playlistvideos">
    <v-container>
      <v-row v-for="item in list" :key="item.id" style="margin: 50px 0 50px 0">
        <v-col cols="6">
          <div class="videodescription" :id="item.id">
            <div class="content">
              <div>
                <h3 class="mb-5">
                  슬기로운 싸피생활
                </h3>
                <p style="font-weight: light; ">
                  Lorem ipsum dolor sit amet consectetur adipisicing elit. Alias
                  quis consequuntur magni accusantium similique corporis dolores
                  voluptatum ipsum distinctio maiores. Voluptas ullam ratione
                  nostrum non maiores quia molestiae aperiam. Magnam.
                </p>
              </div>
            </div>
          </div>
        </v-col>

        <v-col cols="1"></v-col>

        <v-col cols="5" justify="center">
          <div class="card" @mouseover="isHover = !isHover">
            <div class="imgBx" :data-text="item.playListName">
              <img class="bigimg" src="@/assets/jun.png" />
              <img
                class="smallimg"
                src="@/assets/playBtn.png"
                @click="goLecture(item.playListName)"
              />
            </div>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class PlayListRoadmap extends Vue {
  isHover = true;
  list = [
    {
      id: 1,
      playListName: "junho"
    },
    {
      id: 2,
      playListName: "junho"
    },
    {
      id: 3,
      playListName: "junho"
    },
    {
      id: 4,
      playListName: "junho"
    },
    {
      id: 5,
      playListName: "junho"
    },
    {
      id: 6,
      playListName: "junho"
    },
    {
      id: 7,
      playListName: "junho"
    },
    {
      id: 8,
      playListName: "junho"
    },
    {
      id: 9,
      playListName: "junho"
    }
  ];
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
  height: 250px;
  background: #fff;
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
  padding: 20px;
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
  width: 150px;
  height: 150px;
  left: -75px;
  top: calc(50% - 75px);
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
  right: 0;
  width: calc(100% - 75px);
  height: 100%;
  padding: 20px;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.card .content h3 {
  margin-bottom: 5px;
  font-size: 24px;
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
