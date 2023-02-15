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
        <v-btn @click="submitArticle">작성 완료</v-btn>
        <v-btn @click="() => $router.push({ name: 'home'})">돌아가기</v-btn>
    </div>
    </v-form>
</template>

<script>
import axios from 'axios';
import CKEditor from '@ckeditor/ckeditor5-vue2';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

export default {
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
.controls {
    margin-top: 15px;
}
.ck-editor__editable_inline {
    min-height: 500px;
}
</style>