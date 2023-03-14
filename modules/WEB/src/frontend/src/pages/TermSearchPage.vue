<template>
  <div id="term_search_page">
    <common-form>
      <search-component
          :cur-page="curPage"
          :has-more="terms.length === pageSize"
          v-model="searchWildcard"
          @search="searchTerms"
      >
        <my-list :not-empty="terms.length > 0">
          <my-list-item v-for="termIt in terms" :key="termIt.id" :hover="true" class="value">
            <term-item :term="termIt"/>
          </my-list-item>
        </my-list>
      </search-component>
      <separate-line class="separator"/>
      <add-term>
        <my-label>Haven't found term? Add new one!</my-label>
      </add-term>
    </common-form>
  </div>
</template>

<script lang="ts">
  import {defineComponent} from "vue";
  import {TermData, TermService} from "@/services/TermService";
  import router from "@/router/routes";
  import TermItem from "@/components/results/Term/TermItem.vue";
  import SearchComponent from "@/components/SearchComponent.vue";
  import AddTerm from "@/components/AddTerm.vue";
  import pathMixin from "@/components/mixins/pathMixin";
  import MyList from "@/components/UI/list/CommonList.vue";
  import MyListItem from "@/components/UI/list/ListItem.vue";

  export default defineComponent({
    name: 'TermSearchPage',
    mixins: [pathMixin],
    components: {
      MyListItem,
      MyList,
      AddTerm,
      SearchComponent,
      TermItem
    },
    data() : {searchWildcard: string, curPage: number, pageSize: number, terms: TermData[]} {
      return {
        searchWildcard: this.$route.query.term_search_name ? this.$route.query.term_search_name as string : 'object',
        curPage: this.$route.query.term_search_page ? parseInt(this.$route.query.term_search_page as string) : 1,
        pageSize: this.$route.query.term_search_amount ? parseInt(this.$route.query.term_search_amount as string) : 6,
        terms: [],
      }
    },
    computed: {
      termS: () => new TermService()
    },
    async created() {
      this.terms = await this.searchTerms(1);
    },
    methods: {
      async searchTerms(page: number) : Promise<TermData[]> {
        this.curPage = page;
        if (this.searchWildcard) {
          await router.push({query: {
              term_search_name: this.searchWildcard,
              term_search_page: this.curPage,
              term_search_amount: this.pageSize
          }});
          const response = await this.termS.getTerms(this.getQuery());
          return this.terms = response.terms;
        }
        return [];
      },
    },
  })
</script>

<style scoped>
  #term_search_page {
    width: 750px;
    height: auto;
    min-height: 400px;
    min-width: 750px;

    padding: 20px 0;
    margin: 0px auto 20px auto;
  }

  #term_search_page * {
    width: 100%;
  }

  #term_search_page form > *{
    width: 75%;
  }

  .separator {
    margin-top: 20px;
    padding-bottom: 10px;
  }
</style>