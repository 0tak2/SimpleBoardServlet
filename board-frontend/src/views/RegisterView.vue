<template>
  <v-container>
    <register-control />
  </v-container>
</template>

<script>
  import RegisterControl from '@/components/RegisterControl.vue';

  export default {
    name: 'RegisterView',
    components: {
      RegisterControl
    },
  }
</script>
