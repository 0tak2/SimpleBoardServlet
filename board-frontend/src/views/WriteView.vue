<template>
  <v-container>
    <login-check />
    <write-control />
  </v-container>
</template>

<script>
  import LoginCheck from "../components/LoginCheck.vue";
  import WriteControl from "../components/WriteControl.vue";
  export default {
    name: 'WriteView',

    components: {
      LoginCheck,
      WriteControl
    },
  }
</script>
