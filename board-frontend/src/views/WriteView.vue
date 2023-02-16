<template>
  <v-container>
    <login-check />
    <write-control />
  </v-container>
</template>

<script>
  import LoginCheck from "../components/common/LoginCheck.vue";
  import WriteControl from "../components/board/WriteControl.vue";
  export default {
    name: 'WriteView',

    components: {
      LoginCheck,
      WriteControl
    },
  }
</script>
