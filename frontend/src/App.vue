<template>
  <v-app id="app">
    <app-navbar />
    <app-main style=" margin-top: 64px;" />
    <go-top />
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import AppNavbar from "@/components/app/AppNavbar.vue";
import AppMain from "@/components/app/AppMain.vue";
import GoTop from "@/components/GoTop.vue";

@Component({
  components: {
    AppNavbar,
    AppMain,
    GoTop
  }
})
export default class App extends Vue {}
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
