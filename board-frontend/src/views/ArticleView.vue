<template>
  <v-container>
    <login-check />
    <article-panel :articleNum="Number($route.params.articleNum)" />
  </v-container>
</template>

<script>
import ArticlePanel from '@/components/ArticlePanel.vue'
import LoginCheck from '@/components/LoginCheck.vue'
  export default {
    name: 'ArticleView',
    components: {
        ArticlePanel,
        LoginCheck
    }
  }
</script>
