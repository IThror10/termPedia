<template>
  <div class="lit">
    <div class="lit-header">
      <my-label class="lit-label">Literature</my-label>
      <my-button @click="searchLit(curPage)">Refresh</my-button>
    </div>
    <separate-line/>

    <div class="add-lit" v-if="!toCreate">
      <DropdownInput
          placeholder="Lit Name..."
          v-model="searchName"
          @add="addLit"
      >
        <my-list :not-empty="toSelect.length > 0">
          <framed-list-item v-for="(book, index) in toSelect" :key="book.id" :hover="true" @click="choose(index)">
            <lit-name :book="book"/>
          </framed-list-item>
        </my-list>
      </DropdownInput>
      <div class="error" v-if="hasError">
        <error-message>{{errorMsg}}</error-message>
      </div>
    </div>
    <div class="add-lit-form" v-else>
      <add-lit
        :form="form"
        @close="toCreate=false"
        @create="createLit"
      />
    </div>
    <separate-line/>

    <search-component class="lit-searcher"
        @search="searchLit"
        :has-input="false"
        :cur-page="curPage"
        :has-more="pageSize === data.length"
    >
      <my-list :not-empty="data.length > 0">
        <list-item v-for="book in data" :key="book.lid">
          <lit-item :term-id="termId" :lit="book"/>
        </list-item>
      </my-list>
    </search-component>
  </div>
</template>

<script lang="ts">
  import {defineComponent, ref} from "vue";
  import SearchComponent from "@/components/SearchComponent.vue";
  import {LiteratureData, LiteratureService, RatedLiteratureData} from "@/services/LiteratureService";
  import DropdownInput from "@/components/UI/composits/DropdownInput.vue";
  import pathMixin from "@/components/mixins/pathMixin";
  import AddLit from "@/components/AddLit.vue";
  import errorMixin from "@/components/mixins/errorMixin";
  import MyList from "@/components/UI/list/CommonList.vue";
  import LitName from "@/components/results/Literature/LitName.vue";
  import FramedListItem from "@/components/UI/list/FramedListItem.vue";
  import ListItem from "@/components/UI/list/ListItem.vue";
  import LitItem from "@/components/results/Literature/LitItem.vue";

  export default defineComponent({
    name: "LitTermComponent",
    components: {LitItem, ListItem, FramedListItem, LitName, MyList, AddLit, SearchComponent, DropdownInput},
    mixins: [pathMixin, errorMixin],

    data() : {curPage: number, pageSize: number, data: RatedLiteratureData[], searchName: string,
      toSelect: LiteratureData[], selectIndex: number | null, ignoreChange: boolean, toCreate: boolean} {
      return {
        curPage: 1,
        pageSize: 3,
        data: [],

        searchName: '',

        toSelect: [],
        selectIndex: null,
        ignoreChange: false,
        toCreate: false,
      }
    },
    props: {
      termId: {
        type: Number,
        required: true
      }
    },
    computed: {
      ls: () => new LiteratureService()
    },
    watch: {
      async searchName(newValue) {
        if (this.ignoreChange) {
          this.ignoreChange = false;
          return
        }

        this.selectIndex = null;
        if (newValue.length < 2)
          this.toSelect = [];
        else
          this.toSelect = await this.ls.searchByName('book_name=' + this.searchName).then(response => response.lit);
      }
    },
    async created() {
      await this.searchLit(this.curPage)
    },
    methods: {
      async searchLit(page: number) {
        this.curPage = page;
        const query = `search_page=${this.curPage}&search_amount=${this.pageSize}`
        this.data = await this.ls.searchByTermId(this.termId, query).then(response => response.lit);
      },
      async addLit() {
        if (this.selectIndex == null) {
          this.toCreate = true;
          this.form.name = this.searchName;
        } else {
          await this.ls.connectToTerm(this.termId, this.toSelect[this.selectIndex].lid);
          this.searchName = '';
        }
      },
      async createLit(form: LiteratureData) {
        const response = await this.ls.createLit(form);
        if (typeof response === 'boolean') {
          this.toCreate = false;
        } else {
          this.errorMsg = response.error;
        }
      },
      choose(index: number) {
        const book = this.toSelect[index];
        this.selectIndex = index;
        this.searchName = book.type + ': ' + book.name + '(' + book.year + ')';
        this.ignoreChange = true;
      },
    },
    setup() {
      const form = ref<LiteratureData>({
        lid: 0,
        name: '',
        year: 2022,
        type: 'book',
        authors: [],
      })
      return {form}
    }
  })
</script>

<style scoped>
  .lit {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .lit-label {
    display: flex;
    justify-content: center;
    width: 40%;
  }
  .lit-header {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 10px 0;
  }
  .lit-searcher {
    margin: 20px 0;
  }
  .add-lit {
    z-index: 2;
  }
  .add-lit * {
    z-index: 3;
  }
</style>