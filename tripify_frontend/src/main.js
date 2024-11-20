//import "./assets/main.css";

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from "pinia-plugin-persistedstate"

import App from './App.vue';
import router from './router';

import BootstrapVue3 from 'bootstrap-vue-3';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';

const app = createApp(App);

const pinia = createPinia();
app.use(pinia);
pinia.use(piniaPluginPersistedstate);

app.use(router);
app.use(BootstrapVue3);

app.mount('#app');
