<template>
  <v-container>
    <login-check />
    <v-main>
      <article-panel :articleNum="Number($route.params.articleNum)" />
    </v-main>
  </v-container>
</template>

<script>
import ArticlePanel from '@/components/board/ArticlePanel.vue'
import LoginCheck from '@/components/common/LoginCheck.vue'
  export default {
    name: 'ArticleView',
    components: {
        ArticlePanel,
        LoginCheck
    }
  }
</script>
