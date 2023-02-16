<template>
  <v-form>
    <v-text-field
        v-model="articleTitle"
        label="제목"
        required
    ></v-text-field>
    <ckeditor
        :editor="editor"
        v-model="articleContent"
        :config="editorConfig"
    >
    </ckeditor>
    <div class="controls">
        <v-btn @click="submitArticle">수정 완료</v-btn>
        <v-btn @click="() => $router.push({ name: 'viewArticle', params: { articleNum: articleNum }})">돌아가기</v-btn>
    </div>
  </v-form>
</template>

<script>
import axios from 'axios';
import CKEditor from '@ckeditor/ckeditor5-vue2';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

export default {
    props: {
        articleNum: Number
    },
    data() {
        return {
            articleTitle: '',
            articleContent: '',
            editor: ClassicEditor,
            editorConfig: {
                toolbar: [ 'heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote' ],
                heading: {
                    options: [
                        { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
                        { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
                        { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' }
                    ]
                }
            }
        }
    },
    components: {
        ckeditor: CKEditor.component
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
                if (error.response.status === 401) {
                    this.$router.push({ name: 'login'});
                    return;
                }
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

<style scoped>
.controls {
    margin-top: 15px;
}
</style>