<template>
  <v-card
    elevation="2">
    <v-card-subtitle>
        {{ comment.memberName }}({{ comment.commentAuthor }}) &nbsp;&nbsp;
        {{ comment.commentDate }}
    </v-card-subtitle>
    <v-card-text>
        {{ comment.commentContent }}
    </v-card-text>
  </v-card>
</template>

<script>
export default {
    props: {
        comment: Object
    }
}
</script>

<style scoped>
.v-card {
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>