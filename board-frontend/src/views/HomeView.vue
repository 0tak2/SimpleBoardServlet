<template>
  <v-container>
    <login-check />
    <v-main>
      <v-row>
        <v-col cols=3>
          <user-panel />
        </v-col>
        <v-col cols=8 offset=1>
          <div class="text-right">
            <router-link :to="{ name: 'writeArticle' }" id="new-article-btn">
              <v-icon>
                mdi-pen-plus
              </v-icon> 새로운 글
            </router-link>
          </div>
          <article-list />
        </v-col>
      </v-row>
    </v-main>
  </v-container>
</template>

<script>
  import UserPanel from '../components/UserPanel.vue';
  import ArticleList from '../components/ArticleList.vue';
  import LoginCheck from '../components/LoginCheck.vue';

  export default {
    name: 'HomeView',

    components: {
      LoginCheck,
      UserPanel,
      ArticleList
    },
  }
</script>

<style scoped>
#new-article-btn {
  text-decoration-line: none;
  color: black;
}
</style>