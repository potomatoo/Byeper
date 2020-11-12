// @ts-ignore
import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import "./styles/tiptap/main.scss";
import GAuth from "vue-google-oauth2";

const gauthOption = {
  clientId:
    "354609671084-gq057289qgkq7bjjhn0t2ertcot4prak.apps.googleusercontent.com",
  scope: "profile email",
  prompt: "select_account"
};

Vue.use(GAuth, gauthOption);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h: (arg0: typeof App) => any) => h(App)
}).$mount("#app");
