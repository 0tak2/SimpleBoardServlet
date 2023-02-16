<template>
  <v-card
  elevation="3"
  outlined
  >
    <v-card-text>
      {{ this.$store.state.userName }}
      ({{ this.$store.state.userID }})님 로그인하셨습니다.
    </v-card-text>
    <v-card-actions>
      <v-btn text @click="requestLogout">로그아웃</v-btn>
      <v-btn text @click="() => $router.push({ name: 'editMemberInfo'} )">정보 수정</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import axios from 'axios';
export default {
  methods: {
    requestLogout() {
      axios.post(this.baseUrl + 'logout', {},
      {
        withCredentials: true
      })
      .then(() => {
          this.$store.state.userID = '';
          this.$store.state.userName = '';

          localStorage.removeItem("userID");
          localStorage.removeItem("userName");

          this.$router.push({ name: 'login'});
          console.log('[Success] 로그아웃');
      })
      .catch((error) => {
          if (error.response.status === 401) {
              this.$store.state.userID = '';
              this.$store.state.userName = '';

              localStorage.removeItem("userID");
              localStorage.removeItem("userName");

              this.$router.push({ name: 'login'});
              console.error('[ErrorButContinued] 로그아웃'); // 세션이 이미 만료됨
              return;
          }
          console.error('[Error] 로그아웃');
          console.error(error);
      })
    }
  }
}
</script>

<style>

</style>