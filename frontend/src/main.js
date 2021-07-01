import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import "material-design-icons-iconfont/dist/material-design-icons.css";
import "./api";
import store from "./store";
import stripe from "./plugins/stripePlugin"

Vue.config.productionTip = false;

new Vue({
  router,
  vuetify,
  stripe,
  store,
  render: (h) => h(App),
}).$mount("#app");
