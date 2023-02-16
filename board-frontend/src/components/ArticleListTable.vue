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
          <td><router-link :to="{ name: 'viewArticle', params: { articleNum: item.articleNum }}">{{ item.articleTitle }}</router-link></td>
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
export default {
    props: {
        articles: Array
    }
}
</script>

<style>

</style>