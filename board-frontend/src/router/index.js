import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import ArticleView from '../views/ArticleView.vue'
import WriteView from '../views/WriteView.vue'
import EditView from '../views/EditView.vue';
import RegisterView from '../views/RegisterView.vue';
import EditMemberView from '../views/EditMemberView.vue';

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
    path: '/write',
    name: 'writeArticle',
    component: WriteView
  },
  {
    path: '/:articleNum',
    name: 'viewArticle',
    component: ArticleView
  },
  {
    path: '/edit/:articleNum',
    name: 'editArticle',
    component: EditView
  },
  {
    path: '/member/register',
    name: 'registerMember',
    component: RegisterView
  },
  {
    path: '/member/edit',
    name: 'editMemberInfo',
    component: EditMemberView
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
