<template>
  <div>
    <login-check />
    <user-panel />
    <article-list />
  </div>
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
