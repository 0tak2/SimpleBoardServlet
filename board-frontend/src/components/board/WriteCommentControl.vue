<template>
  <div class="d-flex">
    <v-text-field
        v-model="commentContent"
        label="댓글 내용"
        required
    ></v-text-field>
    <v-btn @click="requestEdit">작성 완료</v-btn>
  </div>
</template>

<script>
import axios from 'axios';
export default {
    props: {
        articleNum: Number
    },
    data() {
        return {
            commentContent: ''
        }
    },
    methods: {
        requestEdit() {
            axios.post(this.baseUrl + 'comment', {
                commentContent: this.commentContent,
                articleNum: String(this.articleNum)
            },
            {
                withCredentials: true
            })
            .then((result) =>{
                console.log('[Success] 댓글 작성')
                console.log(result);
                this.$router.go(0);
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    this.$router.push({ name: 'login'});
                    return;
                }
                console.error('[Error] 댓글 작성')
                console.error(error);
            })
        }
    }
}
</script>

<style scoped>
div {
    margin: 10px 8px;
}

.v-btn {
    margin-top: 18px;
}
</style>