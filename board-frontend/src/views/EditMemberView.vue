<template>
  <v-container>
    <login-check />
    <v-main>
      <edit-member-control />
    </v-main>
  </v-container>
</template>

<script>
  import LoginCheck from "../components/common/LoginCheck.vue";
  import EditMemberControl from '@/components/member/EditMemberControl.vue';

  export default {
    name: 'EditView',

    components: {
      LoginCheck,
      EditMemberControl
    },
  }
</script>
