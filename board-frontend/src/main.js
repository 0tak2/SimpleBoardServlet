import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import store from  './store.js'
import CKEditor from '@ckeditor/ckeditor5-vue2';

Vue.config.productionTip = false;

Vue.prototype.baseUrl = 'http://localhost:8080/boardApi/';

Vue.use(CKEditor);

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app');