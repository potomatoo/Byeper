<template>
  <div v-if="AllPlayList">
    <v-row style="margin: 20px 10%">
      <v-row>
        <v-col
          v-for="playList in AllPlayList"
          :key="playList.playlistId"
          class="d-flex child-flex"
          cols="4"
          md="4"
          sm="6"
          @click="pushPlayList(playList.playlistId)"
        >
          <v-hover v-slot="{ hover }">
            <div
              class="mx-auto"
              color="grey lighten-4"
              max-width="100px"
              style="border: 0px; cursor: pointer;"
            >
              <v-img :aspect-ratio="16 / 9" :src="playList.playlistImg">
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
                    <div style="color: white; font-size: 0.3em">
                      자세히 보기
                      <span class="mdi mdi-launch"></span>
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
                        {{ playList.playlistTitle }}
                      </div>
                    </v-row>
                    <v-row>
                      <div
                        style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"
                      >
                        {{ playList.userId }}
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
import { Component, Vue, Watch } from "vue-property-decorator";
import router from "../../router";
import { namespace } from "vuex-class";
import { PlayList } from "../../store/PlayList.interface";

const PlayListModule = namespace("PlayListModule");
const AccountsModule = namespace("AccountsModule");

@Component
export default class AllPlayList extends Vue {
  @PlayListModule.State AllPlayList!: PlayList[] | null;
  @PlayListModule.State scrollEnd!: boolean;
  @PlayListModule.Mutation SET_PLAYLIST_ZERO: any;
  @PlayListModule.Action FETCH_ALL_PLAYLIST: any;
  @AccountsModule.Getter isLoggedIn!: boolean;
  @AccountsModule.State token!: any;

  scrollHeight = 0;
  start = 0;

  scroll() {
    window.onscroll = () => {
      const ceilBottomOfWindow =
        Math.ceil(window.pageYOffset) + window.innerHeight ===
        document.documentElement.offsetHeight;

      const plusBottomOfWindow =
        Math.ceil(window.pageYOffset) + window.innerHeight + 1 ===
        document.documentElement.offsetHeight;

      if (
        (ceilBottomOfWindow || plusBottomOfWindow) &&
        !this.scrollEnd &&
        this.$route.name === "Home"
      ) {
        ++this.start;
        this.FETCH_ALL_PLAYLIST({
          start: this.start
        });
      }
    };
  }

  mounted() {
    this.scroll();
    this.scrollHeight = window.innerHeight;
  }

  pushPlayList(playListId: string) {
    this.$router.push({
      name: "PlayList",
      params: { playListId: playListId }
    });
  }

  @Watch("isLoggedIn", { immediate: true })
  async fetchAllPlayList() {
    await this.SET_PLAYLIST_ZERO();
    this.start = 0;
    this.FETCH_ALL_PLAYLIST({
      start: this.start
    });
  }

  destroyed() {
    this.SET_PLAYLIST_ZERO();
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
