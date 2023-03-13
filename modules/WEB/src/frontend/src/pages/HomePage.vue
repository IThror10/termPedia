<template>
  <div id="homePage">
    <common-form>
      <div class="options">
        <div class="year_limits">
          <label-input type="number" label="YearStart" v-model="yearStart"/>
          <label-input type="number" label="YearEnd" v-model="yearEnd"/>
        </div>
        <div class="checkboxes">
          <label-input type="checkbox" label="Best" v-model="orderByRating"/>
          <label-input type="checkbox" label="Newest" v-model="newestFirst"/>
        </div>
        <select id="searchOption" v-model="searchOption">
          <option value="SearchByTerm">Search By Term</option>
          <option value="SearchByAuthor">Search By Author</option>
          <option value="SearchByName">Search By Name</option>
          <option value="SearchByTags">Search By Tags</option>
        </select>
      </div>
      <separate-line/>
      <search-component
          v-model="searchName"
          @click="searchLit"
          :cur-page="curPage"
          :has-more="data.length == pageSize"
          class="searcher"
      >
        <common-list :not-empty="data.length > 0">
          <list-item v-for="book in data" :key="book.lid" class="value">
            <book-item :lit="book"/>
          </list-item>
        </common-list>
      </search-component>
    </common-form>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import LabelInput from "@/components/UI/composits/LabelInput.vue";
  import {LiteratureData, LiteratureService, RatedLiteratureData} from "@/services/LiteratureService";
  import SearchComponent from "@/components/SearchComponent.vue";
  import router from "@/router/routes";
  import PathMixin from "@/components/mixins/pathMixin";
  import BookItem from "@/components/results/Literature/BookItem.vue";
  import CommonList from "@/components/UI/list/CommonList.vue";
  import ListItem from "@/components/UI/list/ListItem.vue";

  export default defineComponent({
    name: "HomePage",
    mixins: [PathMixin],
    components: {ListItem, CommonList, BookItem, SearchComponent, LabelInput},
    data() : {
      searchOption: string, yearStart: number, yearEnd: number, newestFirst: boolean, orderByRating: boolean,
      curPage: number, pageSize: number, data: RatedLiteratureData[] | LiteratureData[], searchName: string
    }{
      return {
        searchOption: this.$route.query.search_option ? this.$route.query.search_option as string : 'SearchByName',
        yearStart: this.$route.query.year_start ? parseInt(this.$route.query.year_start as string) : 0,
        yearEnd: this.$route.query.year_end ? parseInt(this.$route.query.year_end as string) : 2023,
        newestFirst: this.$route.query.new_first ? this.$route.query.new_first as string === 'true' : false,
        orderByRating: this.$route.query.order_by_rating ? this.$route.query.new_first === 'true' : false,

        curPage: this.$route.query.search_page ? parseInt(this.$route.query.search_page as string) : 1,
        pageSize: this.$route.query.search_amount ? parseInt(this.$route.query.search_amount as string) : 10,
        data: [],
        searchName: this.$route.query.lit_search_name ? this.$route.query.lit_search_name as string : 'OOP',
      }
    },
    computed: {
      ls: () => new LiteratureService()
    },
    async created() {
      await this.searchLit()
    },
    methods: {
      async searchLit() {
        await router.push({query: {
            search_option: this.searchOption,
            year_start: this.yearStart,
            year_end: this.yearEnd,
            new_first: this.newestFirst ? 'true' : 'false',
            order_by_rating: this.orderByRating ? 'true' : 'false',
            search_page: this.curPage,
            search_amount: this.pageSize
        }});

        switch (this.searchOption) {
          case 'SearchByName':
            this.data = await this.ls.searchByName(`${this.getQuery()}&book_name=${this.searchName}`)
                .then(result => result.lit);
            break;

          case 'SearchByTags':
            this.data = await this.ls.searchByTags(`${this.getQuery()}&tags=${this.searchName}`)
                .then(result => result.lit);
            break;

          case 'SearchByTerm':
            this.data = await this.ls.searchByTermName(`${this.getQuery()}&term_name=${this.searchName}`)
                .then(result => result.lit);
            break;

          case 'SearchByAuthor':
            this.data = await this.ls.searchByAuthor(`${this.getQuery()}&author_name=${this.searchName}`)
                .then(result => result.lit);
            break;
        }
      }
    }
  })
</script>

<style scoped>
  #homePage {
    height: auto;
    min-height: 400px;
    min-width: 950px;

    padding: 20px 0;
    margin: 0px auto 20px auto;
  }

  .searcher {
    min-width: 600px;
  }

  .options {
    display: flex;
    flex-direction: row;
    font-size: 16px;
    margin-bottom: 20px;
  }

  .options > *{
    margin: 0px 10px;
  }

</style>

