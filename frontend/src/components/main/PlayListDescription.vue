<template>
  <div id="playListDescription" v-if="PlayList">
    <div class="buyBtn">
      <v-btn
        v-if="!isBuy"
        class="mx-2"
        dark
        color="#d72632"
        @click="buyPlayList"
      >
        <v-icon dark>
          mdi-playlist-plus
        </v-icon>
        재생목록 추가
      </v-btn>
    </div>
    <div class="banner">
      <v-row justify="space-around">
        <v-col cols="5" lg="5" md="5" sm="12" style="align-self: center">
          <div class="textBox">
            <h2>
              {{ PlayList.playlistTitle }}
            </h2>
            <p>{{ PlayList.playlistDescription }}</p>
          </div>
        </v-col>
        <v-col cols="5" lg="5" md="5" sm="12" style="align-self: center">
          <v-simple-table
            dense
            max-height="100px"
            width="100px"
            class="tableBox"
          >
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
                  v-for="(video, video_index) in PlayListVideos"
                  :key="video.play_id"
                  @click="moveScroll(String(video.play_id))"
                >
                  <td class="text-center" style="cursor: pointer;">
                    {{ video_index + 1 }}
                  </td>
                  <td style="cursor: pointer">{{ video.video_title }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-col>
      </v-row>
    </div>

    <v-snackbar v-model="snackbar" timeout="1500">
      성공적으로 재생목록에 추가되었습니다!

      <template v-slot:action="{ attrs }">
        <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { PlayList } from "../../store/PlayList.interface";

const PlayListModule = namespace("PlayListModule");
const AccountsModule = namespace("AccountsModule");

@Component
export default class PlayListDescription extends Vue {
  @PlayListModule.State PlayList!: PlayList;
  @PlayListModule.State PlayListVideos!: [];
  @PlayListModule.Action FETCH_PLAYLIST_VIDEOS: any;
  @PlayListModule.Action FETCH_PLAYLIST: any;
  @PlayListModule.Action BUY_PLAYLIST: any;
  @AccountsModule.Getter isLoggedIn!: boolean;
  @AccountsModule.Action GOOGLE_LOGIN: any;

  $vuetify: any;
  $gAuth: any;
  isBuy = false;
  snackbar = false;

  headers = [
    {
      text: "커리큘럼",
      align: "start",
      value: "playName",
      groupable: false
    }
  ];

  @Watch("$route", { immediate: true })
  fetchPlayList() {
    this.FETCH_PLAYLIST({ playlistId: Number(this.$route.params.playListId) });
  }

  @Watch("$route", { immediate: true })
  fetchPlayListVideos() {
    this.FETCH_PLAYLIST_VIDEOS({
      playlistId: Number(this.$route.params.playListId)
    });
  }

  moveScroll(scrollId: string) {
    const target: HTMLElement = document.getElementById(
      scrollId
    ) as HTMLElement;
    const scrollLocation: number = target.offsetTop;
    const playListDescription: HTMLElement = document.getElementById(
      "playListDescription"
    ) as HTMLElement;
    const heightOfPlayListDescription: number =
      playListDescription.offsetHeight;
    this.$vuetify.goTo(scrollLocation + heightOfPlayListDescription);
  }

  buyPlayList() {
    if (this.isLoggedIn) {
      this.BUY_PLAYLIST({
        playlistId: Number(this.$route.params.playListId)
      });
      this.isBuy = true;
      this.snackbar = true;
    } else {
      if (
        confirm("로그인이 필요합니다.\n구글 로그인을 하시겠습니까??") === true
      ) {
        this.$gAuth
          .getAuthCode()
          .then((authToken: string) => this.GOOGLE_LOGIN(authToken));
      }
    }
  }
}
</script>

<style scoped>
.banner {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 50px 0 50px;
}

.buyBtn {
  margin: 20px 50px 0 0;
  text-align: end;
}

.banner .textBox {
  position: relative;
  z-index: 2;
  overflow: hidden;
  text-overflow: ellipsis;
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
  margin: 10px 0 0 0;
  font-size: 3em;
}

.banner .textBox p {
  font-size: 1.1em;
  margin: 20px 0 0 0;
}

.banner .imgBx {
  position: relative;
  display: contents;
  width: 128px;
  height: 72px;
  z-index: 1;
}

.banner .imgBx img {
  width: 100%;
  max-height: 200px;
}
</style>
