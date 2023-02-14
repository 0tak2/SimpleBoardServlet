<template>
  <v-card
    elevation="2">
    <v-card-subtitle>
        {{ comment.memberName }}({{ comment.commentAuthor }}) &nbsp;&nbsp;
        {{ comment.commentDate }} &nbsp;&nbsp;
        <span v-if="comment.commentAuthor === $store.state.userID">
            <v-btn
                text
                small
                @click="() => editing = !editing"
                class="smallBtn"
            >{{ editing ? '취소' : '수정' }}</v-btn>
            
            &nbsp;&nbsp;
            
            <v-btn
                text
                small
                color="error"
                class="smallBtn"
                @click="requestDelete"
            >삭제</v-btn>
        </span>
    </v-card-subtitle>
    <v-card-text>
        <span v-if="!editing">
            {{ comment.commentContent }}
        </span>
        <span v-else>
              <div class="d-flex">
                <v-text-field
                    v-model="editingCommentContent"
                    label="댓글 내용"
                    required
                ></v-text-field>
                <v-btn @click="requestEdit" class="editBtn">수정</v-btn>
            </div>
        </span>
    </v-card-text>
  </v-card>
</template>

<script>
import axios from 'axios';
export default {
    props: {
        comment: Object
    },
    data() {
        return {
            editing: false,
            editingCommentContent: this.comment.commentContent
        }
    },
    methods: {
        requestEdit() {
            axios.put(this.baseUrl + 'comment/' + this.comment.commentNum, {
                commentContent: this.editingCommentContent
            },
            {
                withCredentials: true
            })
            .then((result) =>{
                console.log('[Success] 댓글 수정')
                console.log(result);
                this.$router.go(0);
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    this.$router.push({ name: 'login'});
                    return;
                }
                console.error('[Error] 댓글 수정')
                console.error(error);
            })
        },
        requestDelete() {
            axios.delete(this.baseUrl + 'comment/' + this.comment.commentNum,
            {
                withCredentials: true
            })
            .then((result) =>{
                console.log('[Success] 댓글 삭제')
                console.log(result);
                this.$router.go(0);
            })
            .catch((error) => {
                if (error.response.status === 401) {
                    this.$router.push({ name: 'login'});
                    return;
                }
                console.error('[Error] 댓글 삭제')
                console.error(error);
            })
        }
    }
}
</script>

<style scoped>
.v-card {
    margin-top: 10px;
    margin-bottom: 10px;
}
.v-btn, .smallBtn {
    margin-bottom: 15px;
}
.v-btn, .editBtn {
    margin-top: 10px;
}
</style>