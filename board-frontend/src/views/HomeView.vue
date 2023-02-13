<template>
  <div>
    <login-panel />
    <article-list />
    <hello-world />
  </div>
</template>

<script>
  import LoginPanel from '../components/LoginPanel.vue';
  import ArticleList from '../components/ArticleList.vue';
  import HelloWorld from '../components/HelloWorld.vue';

  export default {
    name: 'HomeView',

    components: {
      LoginPanel,
      ArticleList,
      HelloWorld
    },
  }
</script>
