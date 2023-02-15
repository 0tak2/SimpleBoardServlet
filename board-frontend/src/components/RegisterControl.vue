<template>
    <v-main>
        <div
            class="text-h4 mb-5"
        >회원가입
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
                        v-model="userID"
                        label="아이디"
                        required
                    ></v-text-field>
                    <div class="message">
                        <span v-if="userID === ''">생략할 수 없습니다</span>
                        <span v-if="userID.length > 10">10자를 넘을 수 없습니다</span>
                        <span v-if="userID !== '' && !checkResult">사용할 수 없는 아이디입니다.</span>
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
                        <span v-if="userPW === ''">생략할 수 없습니다</span>
                        <span v-if="userPW.length > 10">10자를 넘을 수 없습니다</span>
                    </div>
                </div>

                <v-btn @click="requestLogin">회원가입</v-btn>
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
            userID: '',
            userPW: '',
            checkResult: false
        }
    },
    methods: {
        requestLogin() {
            if (this.userName === ''
                    || this.userID === ''
                    || this.userPW === '') {
                alert('모든 내용을 입력해주세요.');
                return;
            }

            if (this.userName.length > 20
                    || this.userID.length  > 10
                    || this.userPW.length  > 10) {
                alert('최대 글자 수를 초과했습니다.');
                return;
            }

            if (!this.checkResult) {
                alert('이 아이디는 사용할 수 없습니다.')
                return;
            }

            axios.post(this.baseUrl + 'member', {
                userID: this.userID,
                userPW: this.userPW,
                userName: this.userName
            },
            { withCredentials: true })
            .then(() => {
                console.log('[Success] 회원가입');
                alert('새로운 계정 ' + this.userID + '을 잘 만들었습니다.');

                this.$router.push({ name: 'login'});
            })
            .catch((error) => {
                console.error('[Error] 회원가입]');
                console.error(error);
            });
        }
    },
    watch: {
        userID(newVal) {
            axios.get(this.baseUrl + 'member', {
                params: { userID: newVal }
            },
            { withCredentials: true })
            .then(() => {
                console.log('[Success] 중복확인');
                this.checkResult = true;
            })
            .catch((error) => {
                if (error.response.status === 400) {
                    console.error('[ErrorButExpected] 중복확인-중복]');
                    this.checkResult = false;
                }
                console.error('[Error] 중복확인]');
                console.error(error);
                this.checkResult = false;
            });
        }
    }
}
</script>

<style scoped>
.message {
    margin-top: 25px;
    margin-left: 15px;
    color: red;
}
</style>