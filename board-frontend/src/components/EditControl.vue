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
    <v-btn @click="submitArticle">수정 완료</v-btn>
    <v-btn @click="() => $router.push({ name: 'viewArticle', params: { articleNum: articleNum }})">돌아가기</v-btn>
  </v-form>
</template>

<script>
import axios from 'axios';
export default {
    props: {
        articleNum: String
    },
    data() {
        return {
            articleTitle: '',
            articleContent: ''
        }
    },
    methods: {
        submitArticle() {
            axios.put(this.baseUrl + 'article/' + this.articleNum, {
                articleTitle: this.articleTitle,
                articleContent: this.articleContent
            },
            {
                withCredentials: true
            })
            .then((result) =>{
                console.log('[Success] 게시글 수정')
                console.log(result);
                this.$router.push({
                    name: 'viewArticle', params: { articleNum: this.articleNum
                }});
            })
            .catch((error) => {
                console.error('[Error] 게시글 수정')
                console.error(error);
            })
        }
    },
    mounted() {
        axios.get(this.baseUrl + 'article/' + this.articleNum,
        {
            withCredentials: true
        })
        .then((result) => {
            console.log(result);
            this.articleTitle = result.data.article.articleTitle;
            this.articleContent = result.data.article.articleContent;
            console.log('[Success] 수정 전 게시글 불러오기');
        })
        .catch((error) => {
            if (error.response.status === 401) {
                this.$router.push({ name: 'login'});
                return;
            }
            console.error('[Error] 수정 전 게시글 불러오기');
            console.error(error);
        })
    }
}
</script>

<style>

</style>