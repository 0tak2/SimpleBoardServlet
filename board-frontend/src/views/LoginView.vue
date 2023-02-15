<template>
  <v-container>
    <login-control />
  </v-container>
</template>

<script>
  import LoginControl from '@/components/LoginControl.vue';

  export default {
    name: 'HomeView',

    components: {
      LoginControl
    },
  }
</script>
