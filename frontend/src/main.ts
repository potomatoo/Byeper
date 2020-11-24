// @ts-ignore
import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import "./styles/tiptap/main.scss";
import GAuth from "vue-google-oauth2";
import VueYoutube from "vue-youtube";

const gauthOption = {
  clientId:
    `${process.env.VUE_APP_CLIENT_ID}.apps.googleusercontent.com`,
  scope: "profile email",
  prompt: "select_account"
};

Vue.use(VueYoutube);

Vue.use(GAuth, gauthOption);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h: (arg0: typeof App) => any) => h(App)
}).$mount("#app");
