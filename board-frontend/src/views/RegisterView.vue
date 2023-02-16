<template>
  <v-container>
    <register-control />
  </v-container>
</template>

<script>
  import RegisterControl from '@/components/member/RegisterControl.vue';

  export default {
    name: 'RegisterView',
    components: {
      RegisterControl
    },
  }
</script>
