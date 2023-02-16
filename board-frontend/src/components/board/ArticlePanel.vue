<template>
  <div>
    <v-card
        elevation="2">
        <v-card-title>
            {{ article.articleTitle }}
        </v-card-title>

        <v-card-subtitle>
            작성자: {{ article.articleAuthor }}<br>
            작성일자: {{ article.articleDate }}<br>
            <span :class="didLike ? 'indigo--text' : ''" >
                {{ article.articleLike }}
            </span>명이 좋아합니다
            <v-btn x-small text @click="requestLike">
                <v-icon small>
                    {{ didLike ? 'mdi-thumb-down' : 'mdi-thumb-up' }}
                </v-icon>
            </v-btn>
        </v-card-subtitle>
        
        <v-card-text v-html="article.articleContent">
        </v-card-text>
    </v-card>

    <router-link :to="{ name: 'home'}">목록으로</router-link> &nbsp;
    <router-link 
        v-if="article.articleAuthor === $store.state.userID"
        :to="{ name: 'editArticle', params: { articleNum: article.articleNum }}"
    >수정</router-link> &nbsp;
    <a
        v-if="article.articleAuthor === $store.state.userID"
        @click.prevent="requestDelete"
    >삭제</a>

    <div class="comments-wrapper">
        <div class="text-h6">
            댓글
        </div>
        <write-comment-control :articleNum="article.articleNum" />
        <comment-entry v-for="comment in comments" :comment="comment" :key="comment.commentNum" />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import WriteCommentControl from './WriteCommentControl.vue';
import CommentEntry from './CommentEntry.vue';

export default {
    props: {
        articleNum: Number
    },
    components: {
        WriteCommentControl,
        CommentEntry
    },
    data() {
        return {
            article: {},
            comments: [],
            didLike: false
        }
    },
    methods: {
        requestDelete() {
            if(!confirm('정말로 삭제하시겠습니까? 이 작업은 돌이킬 수 없습니다.')) {
                return;
            }

            axios.delete(this.baseUrl + 'article/' + this.articleNum,
            {
                withCredentials: true
            })
            .then(() => {
                console.log('[Success] 게시글 삭제');
                this.$router.push({ name: 'home' });
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    this.$router.push({ name: 'login'});
                    return;
                }
                console.error('[Error] 게시글 삭제');
                console.error(error);
            })
        },
        requestLike() {
            if (!this.didLike) { // 좋아요 안한 상태 -> 좋아요 추가
                axios.post(this.baseUrl + 'like', {
                    articleNum: String(this.article.articleNum)
                },
                {
                    withCredentials: true
                })
                .then((result) => {
                    console.log(result);
                    console.log('[Success] 좋아요 추가');
                    this.$router.go(0);
                })
                .catch((error) => {
                    if (error.response.status === 401) {
                        this.$router.push({ name: 'login'});
                        return;
                    }
                    console.error('[Error] 좋아요 추가');
                    console.error(error);
                })
            } else { // 좋아요 한 상태 -> 좋아요 제거
                axios.delete(this.baseUrl + 'like',
                {
                    data: {
                        articleNum: String(this.article.articleNum)
                    },
                    withCredentials: true
                })
                .then((result) => {
                    console.log(result);
                    console.log('[Success] 좋아요 취소');
                    this.$router.go(0);
                })
                .catch((error) => {
                    console.log(error);
                    if (error.response.status === 401) {
                        this.$router.push({ name: 'login'});
                        return;
                    }
                    console.error('[Error] 좋아요 취소');
                    console.error(error);
                })
            }
        }
    },
    mounted() {
        axios.get(this.baseUrl + 'article/' + this.articleNum,
        {
            withCredentials: true
        })
        .then((result) => {
            this.article = result.data.article;
            this.comments = result.data.comments;
            this.didLike = result.data.didLike;
            console.log('[Success] 게시글 불러오기');
        })
        .catch((error) => {
            if (error.response.status === 401) {
                this.$router.push({ name: 'login'});
                return;
            }
            console.error('[Error] 게시글 불러오기');
            console.error(error);
        })
    }
}
</script>

<style scoped>
.v-card {
    margin-bottom: 8px;
}
.comments-wrapper {
    margin-top: 24px;
}
a {
    text-decoration-line: underline;
}
</style>