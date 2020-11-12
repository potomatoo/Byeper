import Vue from "vue";
import Vuex, { StoreOptions } from "vuex";
import AccountsModule from "./AccountsModule.store";
import LecturesModule from "./LecturesModule.store";
import PlayListModule from "./PlayListModule.store";

Vue.use(Vuex);

export interface RootState {
  version: string;
}

const store: StoreOptions<RootState> = {
  state: {
    version: "1.0.0" // a simple property
  },
  modules: {
    AccountsModule,
    LecturesModule,
    PlayListModule
  }
};

export default new Vuex.Store<RootState>(store);
