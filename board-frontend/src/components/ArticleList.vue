<template>
  <v-simple-table>
    <template v-slot:default>
      <thead>
        <tr>
          <th>
            번호
          </th>
          <th>
            글 제목
          </th>
          <th>
            작성자
          </th>
          <th>
            작성일
          </th>
          <th>
            댓글
          </th>
          <th>
            좋아요
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="item in articles"
          :key="item.articleNum"
        >
          <td>{{ item.articleNum }}</td>
          <td>{{ item.articleTitle }}</td>
          <td>{{ item.memberName }}</td>
          <td>{{ item.articleDate }}</td>
          <td>{{ item.articleComments }}</td>
          <td>{{ item.articleLike }}</td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      articles: []
    }
  },
  mounted() {
    axios.get(this.baseUrl + '/article',
        { withCredentials: true })
    .then((result) => {
      console.log('[Success] 전체 게시글 불러오기');
      this.articles = result.data.articles;
    })
    .catch((error) => {
      console.error('[Error] 전체 게시글 불러오기');
      console.error(error);
    })
  }
}
</script>

<style>

</style>