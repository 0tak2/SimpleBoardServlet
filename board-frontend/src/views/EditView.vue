<template>
  <v-container>
    <login-check />
    <edit-control :articleNum="$route.params.articleNum" />
  </v-container>
</template>

<script>
  import LoginCheck from "../components/LoginCheck.vue";
  import EditControl from "../components/EditControl.vue";
  export default {
    name: 'EditView',

    components: {
      LoginCheck,
      EditControl
    },
  }
</script>
