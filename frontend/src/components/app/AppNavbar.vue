<template>
  <div>
    <v-app-bar style="position: fixed; z-index: 100">
      <v-app-bar-nav-icon
        v-if="$route.name !== 'LecturePage'"
        @click="TOGGLE_SIDEBAR"
      ></v-app-bar-nav-icon>
      <v-col cols="3">
        <v-toolbar-title style="font-weight: bold">VPT</v-toolbar-title>
      </v-col>
      <v-col cols="6" style="padding: 0">
        <v-text-field
          solo
          color="white"
          outlined
          dense
          label="검색"
          prepend-inner-icon="mdi-magnify"
          hide-details="true"
          v-model="inputText"
        >
        </v-text-field>
      </v-col>
      <v-col cols="1" style="margin-left: 30px"></v-col>
      <v-col cols="1">
        <v-btn v-if="!isLoggedIn" @click="toGoogleLogin">
          <v-icon>mdi-account</v-icon>로그인
        </v-btn>
        <v-btn v-if="isLoggedIn" @click="logout">
          <v-icon>mdi-account</v-icon>로그아웃
        </v-btn>
      </v-col>
    </v-app-bar>

    <v-navigation-drawer
      app
      permanent
      v-if="isSidebar"
      style="margin-top: 64px; width: 200px"
    >
      <v-list nav dense>
        <v-list-item-group
          v-model="group"
          active-class="deep-gray--text text--accent-4"
        >
          <v-list-item @click="goHome">
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>홈</v-list-item-title>
          </v-list-item>

          <v-list-item @click="goStudent">
            <v-list-item-icon>
              <v-icon>mdi-book-open-page-variant</v-icon>
            </v-list-item-icon>
            <v-list-item-title>학습 관리</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-fire</v-icon>
            </v-list-item-icon>
            <v-list-item-title>인기</v-list-item-title>
          </v-list-item>

          <v-list-item>
            <v-list-item-icon>
              <v-icon>mdi-play-box-multiple</v-icon>
            </v-list-item-icon>
            <v-list-item-title>구독</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { namespace } from "vuex-class";
const AccountsModule = namespace("AccountsModule");

@Component
export default class AppNavbar extends Vue {
  @AccountsModule.State isSidebar!: boolean;
  @AccountsModule.Getter isLoggedIn!: boolean;
  @AccountsModule.Action GOOGLE_LOGIN: any;
  @AccountsModule.Mutation REMOVE_TOKEN: any;
  @AccountsModule.Mutation TOGGLE_SIDEBAR: any;

  group = null;
  inputText = null;
  $gAuth: any;
  toGoogleLogin() {
    this.$gAuth
      .getAuthCode()
      .then((authToken: string) => this.GOOGLE_LOGIN(authToken));
  }
  goHome() {
    if (this.$route.name !== "Home") {
      this.$router.push({ name: "Home" });
    }
  }
  goStudent() {
    if (this.isLoggedIn) {
      this.$router.push({ name: "StudentPage" });
    } else {
      if (
        confirm("로그인이 필요합니다.\n구글 로그인을 하시겠습니까?") === true
      ) {
        this.$gAuth
          .getAuthCode()
          .then((authToken: string) => this.GOOGLE_LOGIN(authToken));
      }
    }
  }
  logout() {
    // this.LOGOUT();
    this.REMOVE_TOKEN();
    if (this.$route.name !== "Home") {
      this.$router.push({ name: "Home" });
    }
  }
}
</script>

<style scoped></style>
