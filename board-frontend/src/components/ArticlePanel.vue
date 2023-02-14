<template>
  <div>
    <v-card
        elevation="2">
        <v-card-title>
            {{ article.articleTitle }}
        </v-card-title>

        <v-card-subtitle>
            작성자: {{ article.articleAuthor }}<br>
            작성일자: {{ article.articleDate }}
        </v-card-subtitle>
        
        <v-card-text>
            {{ article.articleContent }}
        </v-card-text>
    </v-card>

    <div>
        <comment-entry v-for="comment in comments" :comment="comment" :key="comment.commentNum" />
    </div>

    <router-link :to="{ name: 'home'}">목록으로</router-link><br>
    <router-link 
        v-if="article.articleAuthor === $store.state.userID"
        :to="{ name: 'editArticle', params: { articleNum: article.articleNum }}"
    >수정</router-link>
  </div>
</template>

<script>
import axios from 'axios';
import CommentEntry from './CommentEntry.vue';

export default {
    props: {
        articleNum: Number
    },
    components: {
        CommentEntry
    },
    data() {
        return {
            article: {},
            comments: [],
            didLike: false
        }
    },
    mounted() {
        axios.get(this.baseUrl + 'article/' + this.articleNum,
        {
            withCredentials: true
        })
        .then((result) => {
            console.log(result);
            this.article = result.data.article;
            this.comments = result.data.comments;
            this.didLike = result.data.didLike;
            console.log('[Success] 게시글 불러오기');
        })
        .catch((error) => {
            console.error('[Error] 게시글 불러오기');
            console.error(error);
        })
    }
}
</script>

<style>

</style>