import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import ArticleView from '../views/ArticleView.vue'
import WriteView from '../views/WriteView.vue'
import EditView from '../views/EditView.vue';

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/:articleNum',
    name: 'viewArticle',
    component: ArticleView
  },
  {
    path: '/write',
    name: 'writeArticle',
    component: WriteView
  },
  {
    path: '/edit/:articleNum',
    name: 'editArticle',
    component: EditView
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
