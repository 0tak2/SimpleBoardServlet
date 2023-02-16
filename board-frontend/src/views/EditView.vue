<template>
  <v-container>
    <login-check />
    <v-main>
      <edit-control :articleNum="Number($route.params.articleNum)" />
    </v-main>
  </v-container>
</template>

<script>
  import LoginCheck from "../components/common/LoginCheck.vue";
  import EditControl from "../components/board/EditControl.vue";
  export default {
    name: 'EditView',

    components: {
      LoginCheck,
      EditControl
    },
  }
</script>
