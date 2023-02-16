<template>
  <div>
    <article-list-table :articles="articles" />
    <div class="page-indicator">
      <div>
        <span
          v-for="pageNum in [...Array(numOfPages).keys()]"
          :key="pageNum"
        >
          <span
            class="page-num-link"
            :class="pageNum+1 === currentPage ? 'selected' : ''"
            @click="() => { currentPage = pageNum+1 }"
          >{{ pageNum + 1 }}</span>
          &nbsp;
        </span>
      </div>
      <select v-model="size" >
        <option value="5">(5개씩 보기)</option>
        <option value="10">(10개씩 보기)</option>
        <option value="15">(15개씩 보기)</option>
        <option value="20">(20개씩 보기)</option>
      </select>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import ArticleListTable from './ArticleListTable.vue';

export default {
  components: { ArticleListTable },
  data() {
    return {
      articles: [],
      currentPage: 1,
      size: 5,
      numOfRows: 0
    }
  },
  computed: {
    numOfPages() {
      return Math.ceil((this.numOfRows / this.size));
    }
  },
  methods: {
    changePage() {
      axios.get(this.baseUrl + 'article',
          {
            params: {
              size: String(this.size),
              page: String(this.currentPage)
            },
            withCredentials: true
          }
      )
      .then((result) => {
        console.log('[Success] 전체 게시글 불러오기');
        this.articles = result.data.articles;
        this.numOfRows = result.data.numOfArticles;
      })
      .catch((error) => {
        if (error.response.status === 401) {
            if(this.$route.path === '/login') {
              return;
            }
            this.$router.push({ name: 'login'});
            return;
        }
        console.error('[Error] 전체 게시글 불러오기');
        console.error(error);
      })
    }
  },
  watch: {
    currentPage() {
      this.changePage();
    },
    size() {
      this.currentPage = 1;
      this.changePage();
    }
  },
  mounted() {
    this.changePage(1);
  }
}
</script>

<style scoped>
.page-indicator {
  text-align: center;
  margin-top: 20px;
}

.page-num-link {
  text-decoration-line: underline;
  cursor: pointer;
}

.selected {
  font-weight: bold;
  text-decoration-line: none;
}

.no-underline {
  cursor: none;
  text-decoration-line: none;
}

.size-combobox {
  margin-right: 5px;
  appearance: menulist;
}
</style>