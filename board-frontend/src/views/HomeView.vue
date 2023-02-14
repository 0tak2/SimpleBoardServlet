<template>
  <v-container>
    <login-check />
    <v-row>
      <v-col cols=3>
        <user-panel />
      </v-col>
      <v-col cols=8 offset=1>
        <div class="text-right">
          <router-link :to="{ name: 'writeArticle' }">글쓰기</router-link>
        </div>
        <article-list />
      </v-col>
    </v-row>
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
