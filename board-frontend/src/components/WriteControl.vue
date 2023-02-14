<template>
  <v-form>
    <v-text-field
        v-model="articleTitle"
        label="제목"
        required
    ></v-text-field>
    <v-textarea
        v-model="articleContent"
        label="내용"
        required
    ></v-textarea>
    <v-btn @click="submitArticle">작성 완료</v-btn>
    <v-btn @click="() => $router.push({ name: 'home'})">돌아가기</v-btn>
  </v-form>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            articleTitle: '',
            articleContent: ''
        }
    },
    methods: {
        submitArticle() {
            axios.post(this.baseUrl + 'article', {
                articleTitle: this.articleTitle,
                articleContent: this.articleContent
            },
            {
                withCredentials: true
            })
            .then((result) =>{
                console.log('[Success] 게시글 작성')
                console.log(result);
                this.$router.push({ name: 'home'});
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    this.$router.push({ name: 'login'});
                    return;
                }
                console.error('[Error] 게시글 작성')
                console.error(error);
            });
        }
    }
}
</script>

<style>

</style>