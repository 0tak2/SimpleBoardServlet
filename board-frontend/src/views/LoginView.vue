<template>
  <v-container>
    <login-control />
  </v-container>
</template>

<script>
  import LoginControl from '@/components/member/LoginControl.vue';

  export default {
    name: 'HomeView',

    components: {
      LoginControl
    },
  }
</script>
