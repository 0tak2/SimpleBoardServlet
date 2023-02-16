<template>
  <v-container>
    <login-check />
    <v-main>
      <edit-member-control />
    </v-main>
  </v-container>
</template>

<script>
  import LoginCheck from "../components/LoginCheck.vue";
  import EditMemberControl from '@/components/EditMemberControl.vue';

  export default {
    name: 'EditView',

    components: {
      LoginCheck,
      EditMemberControl
    },
  }
</script>
