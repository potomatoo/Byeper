<template>
  <div>
    <v-row style="margin: 20px 10%">
      <v-row>
        <v-col
          v-for="item in list"
          :key="item.id"
          class="d-flex child-flex"
          cols="4"
          md="4"
          sm="6"
          @click="pushPlayList(item.playListName)"
        >
          <v-hover v-slot="{ hover }">
            <div
              class="mx-auto"
              color="grey lighten-4"
              max-width="100px"
              style="border: 0px; cursor: pointer;"
            >
              <v-img :aspect-ratio="16 / 9" src="@/assets/jun.png">
                <div
                  class="d-flex transition-fast-in-fast-out black darken-2 v-card--reveal display-3 "
                  style="height: 100%; width: 33%; padding: 0px"
                >
                  <div style="color: white; font-size: 20px;">
                    13+
                    <div class="mdi mdi-play-pause text-center"></div>
                  </div>
                </div>

                <v-expand-transition>
                  <div
                    v-if="hover"
                    class="d-flex transition-fast-in-fast-out black darken-2 v-card--reveal display-3 "
                    style="height: 100%;"
                  >
                    <div style="color: white; font-size: 0.5em">
                      <span class="mdi mdi-play-circle"></span>
                      모두 재생
                    </div>
                  </div>
                </v-expand-transition>
              </v-img>
              <v-card-text class="text--primary">
                <v-row style="font-size: 1em;" align="center" justify="start">
                  <v-col cols="2">
                    <img
                      src="@/assets/logo.png"
                      style="height: 40px; width: 40px; border-radius: 50%; background: black;"
                    />
                  </v-col>

                  <v-col cols="10">
                    <v-row>
                      <div
                        style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; font-weight: bold; padding: 0px"
                      >
                        [브이로그] 슬기로운 싸피생활
                      </div>
                    </v-row>
                    <v-row>
                      <div
                        style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"
                      >
                        싸피대장
                      </div>
                    </v-row>
                  </v-col>
                </v-row>
              </v-card-text>
            </div>
          </v-hover>
        </v-col>
      </v-row>
    </v-row>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import router from "../../router";

@Component
export default class AllPlayList extends Vue {
  list = [
    {
      id: 1,
      playListName: "슬기로운 싸피생활"
    },
    {
      id: 2,
      playListName: "슬기로운 싸피생활"
    },
    {
      id: 3,
      playListName: "슬기로운 싸피생활"
    },
    {
      id: 4,
      playListName: "슬기로운 싸피생활"
    },
    {
      id: 5,
      playListName: "슬기로운 싸피생활"
    },
    {
      id: 6,
      playListName: "슬기로운 싸피생활"
    },
    {
      id: 7,
      playListName: "슬기로운 싸피생활"
    },
    {
      id: 8,
      playListName: "슬기로운 싸피생활"
    },
    {
      id: 9,
      playListName: "슬기로운 싸피생활"
    }
  ];

  pushPlayList(playListName: string) {
    this.$router.push({
      name: "PlayList",
      params: { playListName: playListName }
    });
  }
}
</script>

<style scoped>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: 0.8;
  position: absolute;
  width: 100%;
}
</style>
