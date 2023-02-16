<template>
    <v-main>
        <div
            class="text-h4 mb-5"
        >회원 정보 수정
        </div>
        <v-col cols="8">
            <v-form>
                <div class="d-flex">
                    <v-text-field
                        v-model="userName"
                        label="이름"
                        required
                    ></v-text-field>
                    <div class="message">
                        <span v-if="userName === ''">생략할 수 없습니다</span>
                        <span v-if="userName.length > 20">20자를 넘을 수 없습니다</span>
                    </div>
                </div>

                <div class="d-flex">
                    <v-text-field
                        v-model="userPW"
                        label="패스워드"
                        type="password"
                        required
                    ></v-text-field>
                    <div class="message">
                        <span v-if="userPW === ''">공란인 경우 비밀번호를 변경하지 않습니다</span>
                        <span v-if="userPW.length > 10">10자를 넘을 수 없습니다</span>
                    </div>
                </div>

                <div class="d-flex">
                    <v-text-field
                        v-model="userPWCheck"
                        label="패스워드 확인"
                        type="password"
                        required
                    ></v-text-field>
                    <div class="message">
                        <span v-if="userPW !== userPWCheck">패스워드가 다릅니다</span>
                    </div>
                </div>

                <v-btn @click="submit">회원정보 수정</v-btn>
                <v-btn @click="getOut">회원 탈퇴</v-btn>
            </v-form>
        </v-col>
    </v-main>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            userName: '',
            userPW: '',
            userPWCheck: ''
        }
    },
    methods: {
        submit() {
            if(this.userName === '') {
                alert('이름을 입력해주세요.');
                return;
            }

            if(this.userPW !== '' && this.userPW !== this.userPWCheck) {
                alert('비밀번호와 비밀번호 확인란의 값이 다릅니다.');
                return;
            }
            
            axios.put(this.baseUrl + 'member', {
                userID: this.$store.state.userID,
                userPW: this.userPW,
                userName: this.userName
            },
            {
                withCredentials: true
            })
            .then(() =>{
                alert('회원정보 수정에 성공했습니다.');

                this.$store.state.userID = '';
                this.$store.state.userName = '';

                localStorage.removeItem("userID");
                localStorage.removeItem("userName");

                console.log('[Success] 회원정보 수정');
                this.$router.push({ name: 'login'});
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    this.$router.push({ name: 'login'});
                    return;
                }
                console.error('[Error] 회원정보 수정')
                console.error(error);
            })
        },
        getOut() {
            if(!confirm('정말로 탈퇴하시겠습니까? 이 작업은 돌이킬 수 없습니다.')) {
                return;
            }

            axios.delete(this.baseUrl + 'member/' + this.$store.state.userID,
            {
                withCredentials: true
            })
            .then(() =>{
                alert('회원 탈퇴에 성공했습니다.');

                this.$store.state.userID = '';
                this.$store.state.userName = '';

                localStorage.removeItem("userID");
                localStorage.removeItem("userName");

                console.log('[Success] 회원 탈퇴');
                this.$router.push({ name: 'login'});
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    this.$router.push({ name: 'login'});
                    return;
                }
                console.error('[Error] 회원 탈퇴')
                console.error(error);
            })
        }
    },
    mounted() {
        axios.get(this.baseUrl + 'member/' + this.$store.state.userID,
        {
            withCredentials: true
        })
        .then((result) => {
            this.userName = result.data.memberInfo.memberName
            console.log('[Success] 회원정보 수정 전 회원정보 불러오기');
        })
        .catch((error) => {
            if (error.response.status === 401) {
                this.$router.push({ name: 'login'});
                return;
            }
            console.error('[Error] 회원정보 수정 전 회원정보 불러오기');
            console.error(error);
        })
    }
}
</script>

<style scoped>
.controls {
    margin-top: 15px;
}
.message {
    margin-top: 25px;
    margin-left: 15px;
    color: red;
}
</style>