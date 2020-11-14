<template>
  <v-app id="app">
    <app-navbar />
    <app-main style=" margin-top: 64px;" />
    <go-top />
  </v-app>
</template>

<script lang="ts">
import { Component, Vue, Watch } from "vue-property-decorator";
import AppNavbar from "@/components/app/AppNavbar.vue";
import AppMain from "@/components/app/AppMain.vue";
import { User } from "./store/Accounts.interface";
import GoTop from "@/components/GoTop.vue";
import { namespace } from "vuex-class";
const AccountsModule = namespace("AccountsModule");

@Component({
  components: {
    AppNavbar,
    AppMain,
    GoTop
  }
})
export default class App extends Vue {
  @AccountsModule.State user!: User | null;
  @AccountsModule.Mutation FALSE_SIDEBAR: any;
  @AccountsModule.Getter isLoggedIn!: boolean;
  @AccountsModule.Action FETCH_USER_INFO: any;

  @Watch("$route", { immediate: true })
  falseSidebar() {
    this.FALSE_SIDEBAR();
  }

  @Watch("$route", { immediate: true })
  fetchUserInfo() {
    if (this.isLoggedIn && !this.user) {
      this.FETCH_USER_INFO();
    }
  }
}
</script>

<style>
#app {
  /* background-image: url("assets/backgoundColorImg.png"); */
  background-color: #121212;
  animation: fadein 1s;
  -webkit-animation: fadein 1s; /* Safari and Chrome */
}

@keyframes fadein {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}
</style>
