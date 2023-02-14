<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
      <div class="logo">
        Simple Board
      </div>
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',
  created() {
    this.$store.state.userID = localStorage.getItem("userID");
    this.$store.state.userName = localStorage.getItem("userName");
  },
};
</script>

<style scoped>
  .logo {
    font-weight: 800;
    font-size: 20pt;
  }
</style>