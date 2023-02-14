<template>
  <v-container>
    <div
        class="text-h4 mb-5"
    >로그인이 필요한 페이지입니다.
    </div>
    <v-form>
        <v-text-field
            v-model="userID"
            label="아이디"
            required
        ></v-text-field>
        <v-text-field
            v-model="userPW"
            label="패스워드"
            type="password"
            required
        ></v-text-field>
        <v-btn @click="requestLogin">로그인</v-btn>
    </v-form>
  </v-container>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            userID: '',
            userPW: ''
        }
    },
    methods: {
        requestLogin() {
            axios.post(this.baseUrl + 'login', {
                userID: this.userID,
                userPW: this.userPW
            },
            { withCredentials: true })
            .then((response) => {
                console.log(response);
                const userInfo = response.data.userInfo;
                console.log(userInfo);
                this.$store.state.userID = userInfo.memberId;
                this.$store.state.userName = userInfo.memberName;

                this.$router.push({ name: 'home'});
            })
            .catch(() => {
                alert('로그인에 실패하였습니다. 아이디와 비밀번호를 다시 한 번 확인해주세요.');
            });
        }
    }
}
</script>

<style>

</style>