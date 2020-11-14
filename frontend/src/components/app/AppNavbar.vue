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
      style="margin-top: 64px;"
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

          <v-list-item v-if="user && user.userType !== 1" @click="goMyLecture">
            <v-list-item-icon>
              <v-icon>mdi-fire</v-icon>
            </v-list-item-icon>
            <v-list-item-title>내 강의 관리</v-list-item-title>
          </v-list-item>

          <v-list-item
            v-if="user && user.userType !== 1"
            @click="goCreateLecture"
          >
            <v-list-item-icon>
              <v-icon>mdi-play-box-multiple</v-icon>
            </v-list-item-icon>
            <v-list-item-title>새 강의 추가</v-list-item-title>
          </v-list-item>

          <v-dialog v-model="dialog" width="500">
            <template v-slot:activator="{ on, attrs }">
              <v-list-item
                v-if="user && user.userType === 1"
                v-bind="attrs"
                v-on="on"
              >
                <v-list-item-icon>
                  <v-icon>mdi-account-plus</v-icon>
                </v-list-item-icon>
                <v-list-item-title>강사 등록</v-list-item-title>
              </v-list-item>
            </template>

            <v-card>
              <v-card-title class="headline">
                강사등록
              </v-card-title>

              <v-card-text>
                강사로 등록하면 자신의 유튜브 동영상 강의를 등록 가능합니다.<br />
                자신의 동영상 강의를 Byeper 기능을 활용하여 유저가 학습 효율을
                최대화 할 수 있습니다. <br />지금 바로 강사로 등록하려면 확인
                버튼을 누르세요!
              </v-card-text>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" text @click="registerTeacher">
                  확인
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { namespace } from "vuex-class";
import { User } from "../../store/Accounts.interface";

const AccountsModule = namespace("AccountsModule");

@Component
export default class AppNavbar extends Vue {
  @AccountsModule.State isSidebar!: boolean;
  @AccountsModule.State user!: User;
  @AccountsModule.Getter isLoggedIn!: boolean;
  @AccountsModule.Action GOOGLE_LOGIN: any;
  @AccountsModule.Action REGISTER_TEACHER: any;
  @AccountsModule.Mutation REMOVE_TOKEN: any;
  @AccountsModule.Mutation TOGGLE_SIDEBAR: any;

  dialog = false;

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

  goCreateLecture() {
    if (this.isLoggedIn) {
      this.$router.push({ name: "CreateLecture" });
    }
  }

  goMyLecturer() {
    if (this.isLoggedIn) {
      this.$router.push({ name: "instructor" });
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

  async registerTeacher() {
    await this.REGISTER_TEACHER();
    this.dialog = false;
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
