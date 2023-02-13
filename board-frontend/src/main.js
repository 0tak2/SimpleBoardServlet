import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import store from  './store.js'

Vue.config.productionTip = false

Vue.prototype.baseUrl = 'http://localhost:8080/board/';

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')