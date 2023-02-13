<template>
  <v-container>
    <v-row class="text-center">
      <v-btn @click="ajaxTest">AJAX TEST</v-btn>
    </v-row>
  </v-container>
</template>

<script>
  import axios from 'axios';

  export default {
    name: 'HelloWorld',
    data: () => ({
    }),
    methods: {
      ajaxTest() {
        axios.post(this.baseUrl + 'test', {
        userID: 'hong',
        userPW: '1234'
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
          }
    }
  }
</script>
