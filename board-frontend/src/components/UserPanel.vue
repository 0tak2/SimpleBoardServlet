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
      <v-btn text>로그아웃</v-btn>
      <v-btn text>정보 수정</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {

}
</script>

<style>

</style>